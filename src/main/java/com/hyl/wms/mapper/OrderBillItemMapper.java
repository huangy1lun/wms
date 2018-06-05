package com.hyl.wms.mapper;

import com.hyl.wms.domain.OrderBillItem;
import java.util.List;

public interface OrderBillItemMapper {

    int insert(OrderBillItem record);

    void deleteByOrderId(Long id);
}