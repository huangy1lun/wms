package com.hyl.wms.mapper;

import com.hyl.wms.domain.OrderBillItem;

public interface OrderBillItemMapper {

    int insert(OrderBillItem record);

    void deleteByOrderId(Long id);
}