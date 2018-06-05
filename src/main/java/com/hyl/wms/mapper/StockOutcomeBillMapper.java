package com.hyl.wms.mapper;

import com.hyl.wms.domain.StockOutcomeBill;
import com.hyl.wms.query.QueryObject;

import java.util.List;

public interface StockOutcomeBillMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockOutcomeBill record);

    StockOutcomeBill selectByPrimaryKey(Long id);

    List<StockOutcomeBill> selectAll();

    int updateByPrimaryKey(StockOutcomeBill record);


    List<StockOutcomeBill> queryForList(QueryObject qo);

    Integer queryForCount(QueryObject qo);

    void audit(StockOutcomeBill stockIncomeBill);

}