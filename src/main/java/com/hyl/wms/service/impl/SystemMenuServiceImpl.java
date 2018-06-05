package com.hyl.wms.service.impl;

import com.hyl.wms.domain.Employee;
import com.hyl.wms.domain.SystemMenu;
import com.hyl.wms.mapper.SystemMenuMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.ISystemMenuService;
import com.hyl.wms.util.SessionUtil;
import com.hyl.wms.vo.SystemMenusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SystemMenuServiceImpl implements ISystemMenuService {

    @Autowired
    private SystemMenuMapper systemmenuMapper;

    private Map<Long, Long> map = new HashMap<>();

    @Override
    public List<SystemMenu> list() {
        return systemmenuMapper.selectAll();
    }

    @Override
    public void saveOrUpdate(SystemMenu systemmenu) {
        if (systemmenu.getId() != null) {
            systemmenuMapper.updateByPrimaryKey(systemmenu);
        } else {
            systemmenuMapper.insert(systemmenu);
        }
    }

    @Override
    public SystemMenu get(Long id) {
        return systemmenuMapper.selectByPrimaryKey(id);
    }


    @Override
    public void delete(Long id) {
        //先删除子节点
//        systemmenuMapper.deleteByParentId(id);
        //删除自己
//        systemmenuMapper.deleteByPrimaryKey(id);
        //删除中间表
//        systemmenuMapper.deleteRoleAndMenuByMenuId(id);
        deleteTree(id);
        deleteTreeByMap(map);
        this.map = new HashMap<>();
    }


    public void deleteTree(Long id) {
        List<SystemMenu> childs = systemmenuMapper.queryByParentId(id);
        //判断是否有子节点
        if (childs.size() == 0) {
            SystemMenu menu = systemmenuMapper.selectByPrimaryKey(id);
            this.map.put(id,menu.getParent().getId());
        } else {
            SystemMenu menu = systemmenuMapper.selectByPrimaryKey(id);
            if (menu.getParent() == null) {
                this.map.put(id,-1L);
            } else {
                this.map.put(id,menu.getParent().getId());
            }
            for (SystemMenu child : childs) {
                deleteTree(child.getId());
            }
        }

    }

    public void deleteTreeByMap(Map<Long, Long> map) {
        if(map.size() == 0){
            return;
        }
        List<Long> childs = new ArrayList<>();
        List<Long> parents = new ArrayList<>();
        map.forEach((k,v) -> {
            childs.add(k);
            parents.add(v);
        });
        childs.forEach(p -> {
            if (!parents.contains(p)) {
                System.out.println("删除"+ p);
                systemmenuMapper.deleteByPrimaryKey(p);
                systemmenuMapper.deleteRoleAndMenuByMenuId(p);
                this.map.remove(p);
                deleteTreeByMap(this.map);
            }
        });
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count = systemmenuMapper.queryForCount(qo);
        if (count == null) {
            return PageResult.empty(qo.getPageSize());
        }
        List<SystemMenu> list = systemmenuMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }

    @Override
    public List<SystemMenu> getParents(Long parentId) {
        List<SystemMenu> list = new ArrayList<>();
        //判断当前的parentId是否为null
        while (parentId != null) {
            //如果不为null,获取当前菜单的父菜单
            SystemMenu parent = systemmenuMapper.selectByPrimaryKey(parentId);
            //判断父菜单不为null,将父菜单存进集合
            if (parent != null) {
                list.add(parent);
                parent = parent.getParent();
                //没有父菜单
                if (parent == null) break;
                //将parentId覆盖成当前的父菜单的parentId
                parentId = parent.getId();
            }
        }
        //将集合反转
        Collections.reverse(list);
        return list;
    }

    @Override
    public List<SystemMenusVO> loadMenus(String parentSn) {
        //获取当前登录用户
        Employee currentUserMsg = (Employee) SessionUtil.getCurrentUserMsg();
        //判断该用户是否是超级管理员
        if (currentUserMsg.isAdmin()) {
            return systemmenuMapper.loadMenus(parentSn);
        }
        //根据当前用户所拥有的角色去查询对象的菜单
        return systemmenuMapper.loadMenusByEmployeeId(parentSn, currentUserMsg.getId());
    }

    @Override
    public List<SystemMenu> getAllModule() {
        return systemmenuMapper.getAllModule();
    }


}
