package com.hyl.wms.web.controller;

import com.hyl.wms.domain.Brand;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IBrandService;
import com.hyl.wms.util.RequiredPermission;
import com.hyl.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @RequestMapping("list")
    @RequiredPermission("品牌列表")
    public String list(Map map,@ModelAttribute("qo") QueryObject qo) {
        map.put("result", brandService.query(qo));
        return "brand/list";
    }

    @RequestMapping("input")
    @RequiredPermission("品牌新增或删除")
    public String input(Long id, Map map) {
        if (id != null) {
            map.put("brand", brandService.get(id));
        }
        return "brand/input";
    }

    @RequestMapping("saveOrUpdate")
    @RequiredPermission("品牌编辑")
    @ResponseBody
    public JSONResult saveOrUpdate(Brand brand) {
        try {
            brandService.saveOrUpdate(brand);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
        return JSONResult.success("保存成功");
    }

    @RequestMapping("delete")
    @RequiredPermission("品牌删除")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            brandService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("品牌删除成功");
    }


}
