package com.hyl.wms.web.controller;

import com.hyl.wms.domain.StockIncomeBill;
import com.hyl.wms.query.StockIncomeBillQueryObject;
import com.hyl.wms.service.IDepotService;
import com.hyl.wms.service.IStockIncomeBillService;
import com.hyl.wms.service.ISupplierService;
import com.hyl.wms.util.RequiredPermission;
import com.hyl.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("stockincomebill")
public class StockIncomeBillController {

    @Autowired
    private IStockIncomeBillService stockincomebillService;

    @Autowired
    private IDepotService depotService;

    @RequestMapping("list")
    @RequiredPermission("入库订单列表")
    public String list(Map map,@ModelAttribute("qo") StockIncomeBillQueryObject qo) {
        map.put("result", stockincomebillService.query(qo));
        map.put("depots", depotService.list());
        return "stockincomebill/list";
    }

    @RequestMapping("input")
    @RequiredPermission("入库订单新增或删除")
    public String input(Long id, Map map) {
        if (id != null) {
            map.put("stockincomebill", stockincomebillService.get(id));
        }
        map.put("depots", depotService.list());
        return "stockincomebill/input";
    }

    @RequestMapping("saveOrUpdate")
    @RequiredPermission("入库订单编辑")
    @ResponseBody
    public JSONResult saveOrUpdate(StockIncomeBill stockincomebill) {
        try {
            stockincomebillService.saveOrUpdate(stockincomebill);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
        return JSONResult.success("保存成功");
    }

    @RequestMapping("delete")
    @RequiredPermission("入库订单删除")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            stockincomebillService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("订单删除成功");
    }

    @RequestMapping("audit")
    @RequiredPermission("入库订单审核")
    @ResponseBody
    public JSONResult audit(Long id) {
        try {
            stockincomebillService.audit(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("入库审核通过");
    }

    @RequestMapping("view")
    @RequiredPermission("入库审批通过视图")
    public String view(Long id, Map map) {
        if (id != null) {
            map.put("stockincomebill", stockincomebillService.get(id));
        }
        map.put("depots", depotService.list());
        return "stockincomebill/view";
    }

}
