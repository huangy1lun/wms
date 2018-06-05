package com.hyl.wms.web.controller;

import com.alibaba.fastjson.JSON;
import com.hyl.wms.query.ChartOrderQueryObject;
import com.hyl.wms.query.ChartSaleQueryObject;
import com.hyl.wms.service.IBrandService;
import com.hyl.wms.service.IChartOrderService;
import com.hyl.wms.service.IChartSaleService;
import com.hyl.wms.service.IClientService;
import com.hyl.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("chart")
public class ChartSaleController {

    @Autowired
    private IChartSaleService chartSaleService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IBrandService brandService;


    @RequestMapping("sale")
    @RequiredPermission("销售报表")
    public String list(Map map,@ModelAttribute("qo") ChartSaleQueryObject qo) {
        map.put("list", chartSaleService.query(qo));
        map.put("clients", clientService.list());
        map.put("brands", brandService.list());
        map.put("groupByTypes",ChartSaleQueryObject.groupTypes);
        return "chart/sale";
    }

    @RequestMapping("bar")
    @RequiredPermission("销售柱状报表")
    public String bar(Map map,@ModelAttribute("qo") ChartSaleQueryObject qo) {
        List<Map<String, Object>> maps = chartSaleService.query(qo);
        List<Object> groupByTypes = new ArrayList<>();
        List<Object> totalAmount = new ArrayList<>();
        for (Map<String, Object> datas : maps) {
            groupByTypes.add(datas.get("groupByType"));
            totalAmount.add(datas.get("totalAmount"));
        }
        System.out.println(groupByTypes);
        System.out.println(totalAmount);
        map.put("groupByTypes", JSON.toJSONString(groupByTypes));
        map.put("totalAmount", JSON.toJSONString(totalAmount));
        map.put("groupName", JSON.toJSONString(ChartSaleQueryObject.groupTypes.get(qo.getGroupByType())));
        return "chart/bar";
    }


    @RequestMapping("pie")
    @RequiredPermission("销售饼状报表")
    public String pie(Map map,@ModelAttribute("qo") ChartSaleQueryObject qo) {
        List<Map<String, Object>> maps = chartSaleService.query(qo);
        List<Object> groupByTypes = new ArrayList<>();
        List<Map<Object,Object>> totalAmount = new ArrayList<>();
        for (Map<String, Object> datas : maps) {
            Map<Object,Object> dataMap = new LinkedHashMap<>();
            groupByTypes.add(datas.get("groupByType"));
            dataMap.put("value",datas.get("totalAmount"));
            dataMap.put("name", datas.get("groupByType"));
            totalAmount.add(dataMap);
        }
        map.put("groupByTypes", JSON.toJSONString(groupByTypes));
        map.put("totalAmount", JSON.toJSONString(totalAmount));
        map.put("groupName", JSON.toJSONString(ChartSaleQueryObject.groupTypes.get(qo.getGroupByType())));
        return "chart/pie";
    }


}
