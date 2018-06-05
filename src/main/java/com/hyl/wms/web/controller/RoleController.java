package com.hyl.wms.web.controller;

import com.hyl.wms.domain.Role;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IPermissionService;
import com.hyl.wms.service.IRoleService;
import com.hyl.wms.service.ISystemMenuService;
import com.hyl.wms.util.RequiredPermission;
import com.hyl.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ISystemMenuService systemMenuService;

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("list")
    @RequiredPermission("角色列表")
    public String list(Map map, @ModelAttribute("qo") QueryObject qo) {
        map.put("result", roleService.query(qo));
        return "role/list";
    }

    @RequestMapping("input")
    @RequiredPermission("角色新增或删除")
    public String input(Long id, Map map) {
        if (id != null) {
            map.put("role", roleService.get(id));
        }
        //获取过滤所有的子模块,不包含最大的模块
        map.put("menus", systemMenuService.getAllModule());
        map.put("permissions", permissionService.list());
        return "role/input";
    }

    @RequestMapping("saveOrUpdate")
    @RequiredPermission("角色编辑")
    @ResponseBody
    public JSONResult saveOrUpdate(Role role, Long[] ids, Long[] menuIds) {
        try {
            roleService.saveOrUpdate(role, ids, menuIds);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
        return JSONResult.success("保存成功");
    }

    @RequestMapping("delete")
    @RequiredPermission("角色删除")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            roleService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("角色删除成功");
    }


}
