package com.hyl.wms.web.controller;

import com.hyl.wms.domain.Supplier;
import com.hyl.wms.query.QueryObject;
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
@RequestMapping("supplier")
public class SupplierController {

    @Autowired
    private ISupplierService supplierService;

    @RequestMapping("list")
    @RequiredPermission("供应商列表")
    public String list(Map map,@ModelAttribute("qo") QueryObject qo) {
        map.put("result", supplierService.query(qo));
        return "supplier/list";
    }

    @RequestMapping("input")
    @RequiredPermission("供应商新增或删除")
    public String input(Long id, Map map) {
        if (id != null) {
            map.put("supplier", supplierService.get(id));
        }
        return "supplier/input";
    }

    @RequestMapping("saveOrUpdate")
    @RequiredPermission("供应商编辑")
    @ResponseBody
    public JSONResult saveOrUpdate(Supplier supplier) {
        try {
            supplierService.saveOrUpdate(supplier);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
        return JSONResult.success("保存成功");
    }

    @RequestMapping("delete")
    @RequiredPermission("供应商删除")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            supplierService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("供应商删除成功");
    }


}
