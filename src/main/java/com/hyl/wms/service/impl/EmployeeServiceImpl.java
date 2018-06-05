package com.hyl.wms.service.impl;

import com.hyl.wms.domain.Employee;
import com.hyl.wms.exception.LogicException;
import com.hyl.wms.mapper.EmployeeMapper;
import com.hyl.wms.mapper.PermissionMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IEmployeeService;
import com.hyl.wms.service.IPermissionService;
import com.hyl.wms.util.MD5;
import com.hyl.wms.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private IPermissionService permissionService;

    @Override
    public List<Employee> list() {
        return employeeMapper.selectAll();
    }

    @Override
    public void saveOrUpdate(Employee employee, Long[] ids) {
        if(employee.getId() != null){
            //编辑
            employeeMapper.updateByPrimaryKey(employee);
            //打破关系
            employeeMapper.deleteRelationEmployeeAndRole(employee.getId());
        } else {
            //新建
            employee.setPassword(MD5.encode(employee.getPassword()));
            employeeMapper.insert(employee);
        }
        //建立关系
        if(ids != null && ids.length != 0) {
            for (Long rId : ids) {
                employeeMapper.insertRelationEmployeeAndRole(employee.getId(),rId);
            }
        }
    }

    @Override
    public Employee get(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        employeeMapper.deleteByPrimaryKey(id);
        //打破关系
        employeeMapper.deleteRelationEmployeeAndRole(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count  = employeeMapper.queryForCount(qo);
        if(count == null){
            return PageResult.empty(qo.getPageSize());
        }
        List<Employee> list = employeeMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,list);
    }

    @Override
    public void checkLogin(Employee employee) {
        //判断用户名是否存在
        Employee empByName = employeeMapper.checkLoginByUsername(employee.getName());
        if (empByName == null) {
            throw new LogicException("账户名或密码错误!");
        }
        //判断密码是否正确
//        Employee currentEmp = employeeMapper.checkLoginByUser(employee.getName(), MD5.encode(employee.getPassword()));
//        if (currentEmp == null) {
//            throw new LogicException("账户名或密码错误!");
//        }
        if(!empByName.getPassword().equals(MD5.encode(employee.getPassword()))){
            throw new LogicException("账户名或密码错误!");
        }
        //登录成功
        //保存用户数据session
        SessionUtil.setCurrentUserMsg(empByName);
        //保存用户权限表达式
        Set<String> expression =  permissionService.queryPermissionByEmployeeId(empByName.getId());
        SessionUtil.setCurrentUserPermission(expression);
    }

    @Override
    public void batchDelete(Long[] ids) {
        //删除员工
        employeeMapper.batchDelete(ids);
        //删除员工对象的中间表
        employeeMapper.batchDeleteRelationEmployeeAndRole(ids);
    }
}
