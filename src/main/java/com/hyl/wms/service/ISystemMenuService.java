package com.hyl.wms.service;

import com.hyl.wms.domain.SystemMenu;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.vo.SystemMenuVO;

import java.util.List;

public interface ISystemMenuService {

    List<SystemMenu> list();

    void saveOrUpdate(SystemMenu systemmenu);

    SystemMenu get(Long id);

    void delete(Long id);

    PageResult query(QueryObject qo);

    List<SystemMenuVO> loadMenus(String parentSn);

    List<SystemMenu> getParents(Long parentId);

    List<SystemMenu> getAllModule();
}
