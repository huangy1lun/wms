package com.hyl.wms.service;

import com.hyl.wms.domain.StockIncomeBill;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;

import java.util.List;


public interface IStockIncomeBillService {

  List<StockIncomeBill> list();

  void saveOrUpdate(StockIncomeBill orderBill);

  StockIncomeBill get(Long id);

  void delete(Long id);

  PageResult query(QueryObject qo);

  void audit(Long id);
}
