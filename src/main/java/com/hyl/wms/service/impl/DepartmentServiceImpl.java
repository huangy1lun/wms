package com.hyl.wms.service.impl;

import com.hyl.wms.domain.Department;
import com.hyl.wms.mapper.DepartmentMapper;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hyl.wms.page.PageResult;

import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;


    @Override
    public List<Department> list() {
        return departmentMapper.selectAll();
    }

    @Override
    public void saveOrUpdate(Department department) {
        if(department.getId() != null){
            departmentMapper.updateByPrimaryKey(department);
        } else {
            departmentMapper.insert(department);
        }
    }

    @Override
    public Department get(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        departmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count  = departmentMapper.queryForCount(qo);
        if(count == null){
            return PageResult.empty(qo.getPageSize());
        }
        List<Department> list = departmentMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,list);
    }
}
