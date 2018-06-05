package com.hyl.wms.service;

import com.hyl.wms.query.ChartSaleQueryObject;

import java.util.List;
import java.util.Map;

public interface IChartSaleService {

    List<Map<String,Object>> query(ChartSaleQueryObject qo);

}
