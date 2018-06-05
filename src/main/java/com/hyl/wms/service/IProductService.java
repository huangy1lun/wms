package com.hyl.wms.service;

import com.hyl.wms.domain.Product;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProductService {

  List<Product> list();

  void saveOrUpdate(Product product);

  Product get(Long id);

  void delete(Long id);

  PageResult query(QueryObject qo);

  void batchDelete(Long[] ids);
}

