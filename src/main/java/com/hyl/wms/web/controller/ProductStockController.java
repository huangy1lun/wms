package com.hyl.wms.web.controller;

import com.hyl.wms.query.ProductStockQueryObject;
import com.hyl.wms.service.IBrandService;
import com.hyl.wms.service.IDepotService;
import com.hyl.wms.service.IOrderBillService;
import com.hyl.wms.service.IProductStockService;
import com.hyl.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("productStock")
public class ProductStockController {

    @Autowired
    private IProductStockService productStockService;

    @Autowired
    private IDepotService depotService;

    @Autowired
    private IBrandService brandService;

    @RequestMapping("list")
    @RequiredPermission("及时库存列表")
    public String list(Map map,@ModelAttribute("qo") ProductStockQueryObject qo) {
        map.put("result", productStockService.query(qo));
        map.put("depots", depotService.list());
        map.put("brands", brandService.list());
        return "productStock/list";
    }


}
