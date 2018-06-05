package com.hyl.wms.service.impl;

import com.hyl.wms.domain.Employee;
import com.hyl.wms.domain.SystemMenu;
import com.hyl.wms.mapper.SystemMenuMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.ISystemMenuService;
import com.hyl.wms.util.SessionUtil;
import com.hyl.wms.vo.SystemMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SystemMenuServiceImpl implements ISystemMenuService {

    @Autowired
    private SystemMenuMapper systemmenuMapper;


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

        //删除子菜单
        systemmenuMapper.deleteByParentId(id);
        //删除自己
        systemmenuMapper.deleteByPrimaryKey(id);
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
    public List<SystemMenuVO> loadMenus(String parentSn) {
        //获取当前用户的session
        Employee currentUserMsg = (Employee) SessionUtil.getCurrentUserMsg();
        //判断是否是超级管理员
        if (currentUserMsg.isAdmin()) {
            return systemmenuMapper.loadMenus(parentSn);
        }
        //根据当前用户的id查询对应的菜单
        return systemmenuMapper.queryMenusByEmployeeId(parentSn, currentUserMsg.getId());
    }

    @Override
    public List<SystemMenu> getParents(Long parentId) {
        List<SystemMenu> list = new ArrayList<>();
        while (parentId != null) {
            SystemMenu menu = systemmenuMapper.selectByPrimaryKey(parentId);
            list.add(menu);
            SystemMenu parent = menu.getParent();
            if (parent == null) break;
            parentId = parent.getId();
        }
        Collections.reverse(list);
        return list;
    }

    @Override
    public List<SystemMenu> getAllModule() {
        return systemmenuMapper.getAllModule();
    }
}
