package com.hyl.wms.service.impl;

import com.hyl.wms.domain.Role;
import com.hyl.wms.mapper.RoleMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<Role> list() {
        return roleMapper.selectAll();
    }

    @Override
    public void saveOrUpdate(Role role, Long[] ids, Long[] menuIds) {
        if (role.getId() != null) {
            //编辑
            roleMapper.updateByPrimaryKey(role);
            //打破关系 角色和权限的
            roleMapper.deleteRelationRoleAndPermission(role.getId());
            //打破关系 角色和菜单的
            roleMapper.deleteRelationRoleAndMenu(role.getId());
        } else {
            //新增
            roleMapper.insert(role);
        }
        //保存关系 角色和权限的
        if (ids != null && ids.length != 0) {
            for (Long pId : ids) {
                roleMapper.insertRelationRoleAndPermission(role.getId(), pId);
            }
        }
        //保存关系 角色和菜单的
        if (menuIds != null && menuIds.length != 0) {
            for (Long menuId : menuIds) {
                roleMapper.insertRelationRoleAndMenu(role.getId(),menuId);
            }
        }

    }

    @Override
    public Role get(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        roleMapper.deleteByPrimaryKey(id);
        //打破关系
        roleMapper.deleteRelationRoleAndPermission(id);
        //打破关系
        roleMapper.deleteRelationRoleAndMenu(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count = roleMapper.queryForCount(qo);
        if (count == null) {
            return PageResult.empty(qo.getPageSize());
        }
        List<Role> list = roleMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }
}
