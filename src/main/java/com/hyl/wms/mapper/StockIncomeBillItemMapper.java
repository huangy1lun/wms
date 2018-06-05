package com.hyl.wms.mapper;

import com.hyl.wms.domain.StockIncomeBillItem;
import java.util.List;

public interface StockIncomeBillItemMapper {

    int insert(StockIncomeBillItem record);

    void deleteByBillId(Long id);

    List<StockIncomeBillItem> selectAll();

    void deleteByOrderId(Long id);
}