package com.hyl.wms.web.controller;

import com.hyl.wms.domain.OrderBill;
import com.hyl.wms.query.OrderBillQueryObject;
import com.hyl.wms.service.IOrderBillService;
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
@RequestMapping("orderbill")
public class OrderBillController {

    @Autowired
    private IOrderBillService orderbillService;

    @Autowired
    private ISupplierService supplierService;

    @RequestMapping("list")
    @RequiredPermission("订单列表")
    public String list(Map map,@ModelAttribute("qo") OrderBillQueryObject qo) {
        map.put("result", orderbillService.query(qo));
        map.put("suppliers", supplierService.list());
        return "orderbill/list";
    }

    @RequestMapping("input")
    @RequiredPermission("订单新增或删除")
    public String input(Long id, Map map) {
        if (id != null) {
            map.put("orderbill", orderbillService.get(id));
        }
        map.put("suppliers",supplierService.list());
        return "orderbill/input";
    }

    @RequestMapping("saveOrUpdate")
    @RequiredPermission("订单编辑")
    @ResponseBody
    public JSONResult saveOrUpdate(OrderBill orderbill) {
        try {
            orderbillService.saveOrUpdate(orderbill);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
        return JSONResult.success("保存成功");
    }

    @RequestMapping("delete")
    @RequiredPermission("订单删除")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            orderbillService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("订单删除成功");
    }

    @RequestMapping("audit")
    @RequiredPermission("订单审核")
    @ResponseBody
    public JSONResult audit(Long id) {
        try {
            orderbillService.audit(id);
        } catch (Exception e) {
            e.printStackTrace();
            JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("审核通过");
    }

    @RequestMapping("view")
    @RequiredPermission("审批通过视图")
    public String view(Long id, Map map) {
        if (id != null) {
            map.put("orderbill", orderbillService.get(id));
        }
        map.put("suppliers",supplierService.list());
        return "orderbill/view";
    }

}
