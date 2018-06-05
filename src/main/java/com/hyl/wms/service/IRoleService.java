package com.hyl.wms.service;

import com.hyl.wms.domain.Role;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;

import java.util.List;

public interface IRoleService {

  List<Role> list();

  void saveOrUpdate(Role role, Long[] ids, Long[] menuIds);

  Role get(Long id);

  void delete(Long id);

  PageResult query(QueryObject qo);
}
