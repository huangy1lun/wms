package com.hyl.wms.web.controller;

import com.hyl.wms.domain.Client;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IClientService;
import com.hyl.wms.util.RequiredPermission;
import com.hyl.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("client")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @RequestMapping("list")
    @RequiredPermission("客户列表")
    public String list(Map map,@ModelAttribute("qo") QueryObject qo) {
        map.put("result", clientService.query(qo));
        return "client/list";
    }

    @RequestMapping("input")
    @RequiredPermission("客户新增或删除")
    public String input(Long id, Map map) {
        if (id != null) {
            map.put("client", clientService.get(id));
        }
        return "client/input";
    }

    @RequestMapping("saveOrUpdate")
    @RequiredPermission("客户编辑")
    @ResponseBody
    public JSONResult saveOrUpdate(Client client) {
        try {
            clientService.saveOrUpdate(client);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
        return JSONResult.success("保存成功");
    }

    @RequestMapping("delete")
    @RequiredPermission("客户删除")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            clientService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("客户删除成功");
    }


}
