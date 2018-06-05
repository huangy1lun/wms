package com.hyl.wms.web.controller;

import com.hyl.wms.domain.SystemMenu;
import com.hyl.wms.query.SystemMenuQueryObject;
import com.hyl.wms.service.IPermissionService;
import com.hyl.wms.service.ISystemMenuService;
import com.hyl.wms.util.RequiredPermission;
import com.hyl.wms.vo.JSONResult;
import com.hyl.wms.vo.SystemMenusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("systemmenu")
public class SystemMenuController {

    @Autowired
    private ISystemMenuService systemmenuService;


    @RequestMapping("list")
    @RequiredPermission("菜单列表")
    public String list(Map map,@ModelAttribute("qo") SystemMenuQueryObject qo) {
        if(qo.getParentId() != null) {
            List<SystemMenu> menuList = systemmenuService.getParents(qo.getParentId());
            map.put("mentList",menuList);
        }
        map.put("result", systemmenuService.query(qo));
        return "systemmenu/list";
    }

    @RequestMapping("input")
    @RequiredPermission("菜单编辑")
    public String input(Long id, Map map,Long parentId) {
        if (id != null) {
            map.put("systemmenu", systemmenuService.get(id));
        }
        //获取当前的父级菜单
        if(parentId == null) {
            map.put("parentMenu","根目录");
        } else {
            map.put("parentMenu",systemmenuService.get(parentId).getName());
            map.put("parentId",parentId);
        }
        return "systemmenu/input";
    }

    @RequestMapping("saveOrUpdate")
    @RequiredPermission("菜单新增或更新")
    @ResponseBody
    public JSONResult saveOrUpdate(SystemMenu systemmenu) {
        try {
            systemmenuService.saveOrUpdate(systemmenu);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
        return JSONResult.success("保存成功");
    }

    @RequestMapping("delete")
    @RequiredPermission("菜单删除")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            systemmenuService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("菜单删除成功");
    }

    /**
     * 加载菜单
     * @param ParentSn
     * @return
     */
    @RequestMapping("loadMenus")
    @ResponseBody
    public List<SystemMenusVO> loadMenus(String ParentSn) {
        return systemmenuService.loadMenus(ParentSn);
    }
}
