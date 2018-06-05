package com.hyl.wms.web.controller;

import com.hyl.wms.domain.StockOutcomeBill;
import com.hyl.wms.exception.BillException;
import com.hyl.wms.query.StockOutcomeBillQueryObject;
import com.hyl.wms.service.IClientService;
import com.hyl.wms.service.IDepotService;
import com.hyl.wms.service.IStockOutcomeBillService;
import com.hyl.wms.util.RequiredPermission;
import com.hyl.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("stockoutcomebill")
public class StockOutcomeBillController {

    @Autowired
    private IStockOutcomeBillService stockoutcomebillService;

    @Autowired
    private IDepotService depotService;

    @Autowired
    private IClientService clientService;

    @RequestMapping("list")
    @RequiredPermission("出库订单列表")
    public String list(Map map,@ModelAttribute("qo") StockOutcomeBillQueryObject qo) {
        map.put("result", stockoutcomebillService.query(qo));
        map.put("depots", depotService.list());
        return "stockoutcomebill/list";
    }

    @RequestMapping("input")
    @RequiredPermission("出库订单新增或删除")
    public String input(Long id, Map map) {
        if (id != null) {
            map.put("stockoutcomebill", stockoutcomebillService.get(id));
        }
        map.put("depots", depotService.list());
        map.put("clients", clientService.list());
        return "stockoutcomebill/input";
    }

    @RequestMapping("saveOrUpdate")
    @RequiredPermission("出库订单编辑")
    @ResponseBody
    public JSONResult saveOrUpdate(StockOutcomeBill stockoutcomebill) {
        try {
            stockoutcomebillService.saveOrUpdate(stockoutcomebill);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
        return JSONResult.success("保存成功");
    }

    @RequestMapping("delete")
    @RequiredPermission("出库订单删除")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            stockoutcomebillService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("订单删除成功");
    }

    @RequestMapping("audit")
    @RequiredPermission("出库订单审核")
    @ResponseBody
    public JSONResult audit(Long id) {
        try {
            stockoutcomebillService.audit(id);
        } catch (BillException e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("审核通过");
    }

    @RequestMapping("view")
    @RequiredPermission("出库审批通过视图")
    public String view(Long id, Map map) {
        if (id != null) {
            map.put("stockoutcomebill", stockoutcomebillService.get(id));
        }
        map.put("depots", depotService.list());
        map.put("clients", clientService.list());
        return "stockoutcomebill/view";
    }

}
