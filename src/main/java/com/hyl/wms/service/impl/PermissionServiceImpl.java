package com.hyl.wms.service.impl;

import com.hyl.wms.domain.Permission;
import com.hyl.wms.mapper.PermissionMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IPermissionService;
import com.hyl.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.naming.ldap.Control;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private ApplicationContext ctx;

    @Override
    public List<Permission> list() {
        return permissionMapper.selectAll();
    }

    @Override
    public void saveOrUpdate(Permission permission) {
        if(permission.getId() != null){
            permissionMapper.updateByPrimaryKey(permission);
        } else {
            permissionMapper.insert(permission);
        }
    }

    @Override
    public Permission get(Long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count  = permissionMapper.queryForCount(qo);
        if(count == null){
            return PageResult.empty(qo.getPageSize());
        }
        List<Permission> list = permissionMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,list);
    }

    @Override
    public void load() {
        //获取所有权限
        List<Permission> permissions = permissionMapper.selectAll();
        Set<String> expressions = new HashSet<>();
        for (Permission permission : permissions) {
            expressions.add(permission.getExpression());
        };
        //获取所有Controller
        Map<String, Object> beans = ctx.getBeansWithAnnotation(Controller.class);
        for (Object bean : beans.values()) {
            //获取Controller中所有方法
            Method[] methods = bean.getClass().getMethods();
            for (Method method : methods) {
                //判断方法上是否有注解
               if(method.isAnnotationPresent(RequiredPermission.class)){
                   //获取权限表达式
                   String expression = bean.getClass().getName() + ":" + method.getName();
                   //获取权限名
                   String name = method.getAnnotation(RequiredPermission.class).value();
                   Permission permission = new Permission();
                   permission.setExpression(expression);
                   permission.setName(name);
                    if(expressions.contains(expression)){
                        continue;
                    }
                   //保存数据库
                   permissionMapper.insert(permission);
               }
            }
        }
    }

    @Override
    public Set<String> queryPermissionByEmployeeId(Long id) {
        return permissionMapper.queryPermissionByEmployeeId(id);
    }
}
