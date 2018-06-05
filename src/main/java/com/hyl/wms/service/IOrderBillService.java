package com.hyl.wms.service;

import com.hyl.wms.domain.OrderBill;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;

import java.util.List;

public interface IOrderBillService {

  List<OrderBill> list();

  void saveOrUpdate(OrderBill orderBill);

  OrderBill get(Long id);

  void delete(Long id);

  PageResult query(QueryObject qo);

  void audit(Long id);
}
