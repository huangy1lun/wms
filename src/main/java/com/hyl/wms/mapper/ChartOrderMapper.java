package com.hyl.wms.mapper;

import com.hyl.wms.query.ChartOrderQueryObject;
import com.hyl.wms.query.QueryObject;

import java.util.List;
import java.util.Map;

public interface ChartOrderMapper {

    List<Map<String,Object>> query(ChartOrderQueryObject qo);

}
