package com.hyl.wms.service;


import com.hyl.wms.domain.StockIncomeBill;
import com.hyl.wms.domain.StockOutcomeBill;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import org.springframework.stereotype.Service;

@Service
public interface IProductStockService {

    void stockIncome(StockIncomeBill bill);

    void stockOutcome(StockOutcomeBill bill);

    PageResult query(QueryObject qo);
}
