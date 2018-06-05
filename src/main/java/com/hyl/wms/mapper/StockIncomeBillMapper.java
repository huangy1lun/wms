package com.hyl.wms.mapper;

import com.hyl.wms.domain.StockIncomeBill;
import com.hyl.wms.query.QueryObject;

import java.util.List;

public interface StockIncomeBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockIncomeBill record);

    StockIncomeBill selectByPrimaryKey(Long id);

    List<StockIncomeBill> selectAll();

    int updateByPrimaryKey(StockIncomeBill record);


    List<StockIncomeBill> queryForList(QueryObject qo);

    Integer queryForCount(QueryObject qo);

    void audit(StockIncomeBill bill);

}