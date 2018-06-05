package com.hyl.wms.service;

import com.hyl.wms.domain.SystemMenu;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.vo.SystemMenusVO;

import java.util.List;

public interface ISystemMenuService {

  List<SystemMenu> list();

  void saveOrUpdate(SystemMenu systemMenu);

  SystemMenu get(Long id);

  void delete(Long id);

  PageResult query(QueryObject qo);

  List<SystemMenu> getParents(Long parentId);

  List<SystemMenusVO> loadMenus(String parentSn);

  List<SystemMenu> getAllModule();
}
