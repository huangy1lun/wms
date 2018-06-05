package com.hyl.wms.service;

import com.hyl.wms.domain.StockOutcomeBill;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;

import java.util.List;

public interface IStockOutcomeBillService {

  List<StockOutcomeBill> list();

  void saveOrUpdate(StockOutcomeBill orderBill);

  StockOutcomeBill get(Long id);

  void delete(Long id);

  PageResult query(QueryObject qo);

  void audit(Long id);
}
