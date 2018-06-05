package com.hyl.wms.mapper;

import com.hyl.wms.domain.StockOutcomeBillItem;

public interface StockOutcomeBillItemMapper {

    int insert(StockOutcomeBillItem record);

    void deleteByOrderId(Long id);
}