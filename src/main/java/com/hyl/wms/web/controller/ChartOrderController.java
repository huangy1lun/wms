package com.hyl.wms.web.controller;

import com.hyl.wms.query.ChartOrderQueryObject;
import com.hyl.wms.service.IBrandService;
import com.hyl.wms.service.IChartOrderService;
import com.hyl.wms.service.ISupplierService;
import com.hyl.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("chart")
public class ChartOrderController {

    @Autowired
    private IChartOrderService chartOrderService;

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IBrandService brandService;


    @RequestMapping("order")
    @RequiredPermission("订货报表")
    public String list(Map map,@ModelAttribute("qo") ChartOrderQueryObject qo) {
        map.put("list", chartOrderService.query(qo));
        map.put("suppliers", supplierService.list());
        map.put("brands", brandService.list());
        map.put("groupByTypes",ChartOrderQueryObject.groupTypes);
        return "chart/order";
    }


}
