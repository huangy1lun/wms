package com.hyl.wms.mapper;

import com.hyl.wms.domain.StockIncomeBillItem;

public interface StockIncomeBillItemMapper {

    int insert(StockIncomeBillItem record);

    void deleteByOrderId(Long id);
}