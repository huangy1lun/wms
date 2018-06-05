package com.hyl.wms.web.controller;

import com.hyl.wms.domain.Department;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IDepartmentService;
import com.hyl.wms.util.RequiredPermission;
import com.hyl.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hyl.wms.page.PageResult;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @RequestMapping("list")
    @RequiredPermission("部门列表")
    public String list(Map map,@ModelAttribute("qo") QueryObject qo) {
        map.put("result", departmentService.query(qo));
        return "department/list";
    }

    @RequestMapping("input")
    @RequiredPermission("部门新增或删除")
    public String input(Long id, Map map) {
        if (id != null) {
            map.put("department", departmentService.get(id));
        }
        return "department/input";
    }

    @RequestMapping("saveOrUpdate")
    @RequiredPermission("部门编辑")
    @ResponseBody
    public JSONResult saveOrUpdate(Department department) {
        try {
            departmentService.saveOrUpdate(department);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
        return JSONResult.success("保存成功");
    }

    @RequestMapping("delete")
    @RequiredPermission("部门删除")
    @ResponseBody
    public JSONResult delete(Long id) {
        try {
            departmentService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("部门删除成功");
    }


}
