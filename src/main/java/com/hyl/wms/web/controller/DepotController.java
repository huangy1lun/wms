package com.hyl.wms.web.controller;

import com.hyl.wms.domain.Depot;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IDepotService;
import com.hyl.wms.util.RequiredPermission;
import com.hyl.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("depot")
public class DepotController {

    @Autowired
    private IDepotService depotService;

    @RequestMapping("list")
    @RequiredPermission("仓库列表")
    public String list(Map map,@ModelAttribute("qo") QueryObject qo) {
        map.put("result", depotService.query(qo));
        return "depot/list";
    }

    @RequestMapping("input")
    @RequiredPermission("仓库新增或删除")
    public String input(Long id, Map map) {
        if (id != null) {
            map.put("depot", depotService.get(id));
        }
        return "depot/input";
    }

    @RequestMapping("saveOrUpdate")
    @RequiredPermission("仓库编辑")
    @ResponseBody
    public JSONResult saveOrUpdate(Depot depot) {
        try {
            depotService.saveOrUpdate(depot);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
        return JSONResult.success("保存成功");
    }

    @RequestMapping("delete")
    @RequiredPermission("仓库删除")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            depotService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("仓库删除成功");
    }


}
