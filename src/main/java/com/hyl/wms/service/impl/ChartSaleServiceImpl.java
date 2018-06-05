package com.hyl.wms.service.impl;

import com.hyl.wms.mapper.ChartSaleMapper;
import com.hyl.wms.query.ChartSaleQueryObject;
import com.hyl.wms.service.IChartSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChartSaleServiceImpl implements IChartSaleService {

    @Autowired
    private ChartSaleMapper chartSaleMapper;

    @Override
    public List<Map<String, Object>> query(ChartSaleQueryObject qo) {
        return chartSaleMapper.query(qo);
    }
}
