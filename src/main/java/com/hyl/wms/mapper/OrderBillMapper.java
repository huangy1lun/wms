package com.hyl.wms.mapper;

import com.hyl.wms.domain.OrderBill;
import com.hyl.wms.query.QueryObject;

import java.util.List;

public interface OrderBillMapper {

    int deleteByPrimaryKey(Long id);

    int insert(OrderBill record);

    OrderBill selectByPrimaryKey(Long id);

    List<OrderBill> selectAll();

    int updateByPrimaryKey(OrderBill record);


    List<OrderBill> queryForList(QueryObject qo);

    Integer queryForCount(QueryObject qo);

    void audit(OrderBill orderBill);

}