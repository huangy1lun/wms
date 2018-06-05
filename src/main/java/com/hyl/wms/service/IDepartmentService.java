package com.hyl.wms.service;

import com.hyl.wms.domain.Department;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.page.PageResult;

import java.util.List;

public interface IDepartmentService {

  List<Department> list();

  void saveOrUpdate(Department department);

  Department get(Long id);

  void delete(Long id);

  PageResult query(QueryObject qo);
}
