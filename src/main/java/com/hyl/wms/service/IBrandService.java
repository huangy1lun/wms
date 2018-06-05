package com.hyl.wms.service;

import com.hyl.wms.domain.Brand;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;

import java.util.List;

public interface IBrandService {

  List<Brand> list();

  void saveOrUpdate(Brand brand);

  Brand get(Long id);

  void delete(Long id);

  PageResult query(QueryObject qo);
}
