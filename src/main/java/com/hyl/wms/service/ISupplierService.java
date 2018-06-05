package com.hyl.wms.service;

import com.hyl.wms.domain.Supplier;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;

import java.util.List;

public interface ISupplierService {

  List<Supplier> list();

  void saveOrUpdate(Supplier supplier);

  Supplier get(Long id);

  void delete(Long id);

  PageResult query(QueryObject qo);
}
