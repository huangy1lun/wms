package com.hyl.wms.mapper;

import com.hyl.wms.query.ChartSaleQueryObject;

import java.util.List;
import java.util.Map;

public interface ChartSaleMapper {

    List<Map<String,Object>> query(ChartSaleQueryObject qo);

}
