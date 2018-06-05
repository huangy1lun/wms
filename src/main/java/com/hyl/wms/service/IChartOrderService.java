package com.hyl.wms.service;

import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.ChartOrderQueryObject;

import java.util.List;
import java.util.Map;

public interface IChartOrderService {

    List<Map<String,Object>> query(ChartOrderQueryObject qo);

}
