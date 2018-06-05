package com.hyl.wms.service.impl;

import com.hyl.wms.mapper.ChartOrderMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.ChartOrderQueryObject;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IChartOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChartOrderServiceImpl implements IChartOrderService {

    @Autowired
    private ChartOrderMapper chartOrderMapper;


    @Override
    public List<Map<String, Object>> query(ChartOrderQueryObject qo) {
        return chartOrderMapper.query(qo);
    }
}
