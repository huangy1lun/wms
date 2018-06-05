package com.hyl.wms.web.controller;

import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IPermissionService;
import com.hyl.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("list")
    public String list(Map map,@ModelAttribute("qo") QueryObject qo) {
        map.put("result", permissionService.query(qo));
        return "permission/list";
    }

    @RequestMapping("delete")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            permissionService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("权限删除成功");
    }

    @RequestMapping("load")
    @ResponseBody
    public JSONResult load() {
        permissionService.load();
        return JSONResult.success("权限加载成功");
    }



}
