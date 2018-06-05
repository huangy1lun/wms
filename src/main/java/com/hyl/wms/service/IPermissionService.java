package com.hyl.wms.service;

import com.hyl.wms.domain.Permission;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;

import java.util.List;
import java.util.Set;

public interface IPermissionService {

  List<Permission> list();

  void saveOrUpdate(Permission permission);

  Permission get(Long id);

  void delete(Long id);

  PageResult query(QueryObject qo);

  void load();

  Set<String> queryPermissionByEmployeeId(Long id);
}
