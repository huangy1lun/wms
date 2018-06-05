package com.hyl.wms.web.controller;

import com.hyl.wms.domain.Employee;
import com.hyl.wms.query.EmployeeQueryObject;
import com.hyl.wms.service.IDepartmentService;
import com.hyl.wms.service.IEmployeeService;
import com.hyl.wms.service.IRoleService;
import com.hyl.wms.util.RequiredPermission;
import com.hyl.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IRoleService roleService;

    @RequestMapping("list")
    @RequiredPermission("员工列表")
    public String list(Map map,@ModelAttribute("qo") EmployeeQueryObject qo) {
        map.put("result", employeeService.query(qo));
        map.put("departments",departmentService.list());
        return "employee/list";
    }

    @RequestMapping("input")
    @RequiredPermission("员工编辑")
    public String input(Long id, Map map) {
        if (id != null) {
            map.put("employee", employeeService.get(id));
        }
        map.put("departments",departmentService.list());
        map.put("roles",roleService.list());
        return "employee/input";
    }

    @RequestMapping("saveOrUpdate")
    @RequiredPermission("员工新增或更新")
    @ResponseBody
    public JSONResult saveOrUpdate(Employee employee,Long[] ids) {
        try {
            employeeService.saveOrUpdate(employee,ids);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark("操作失败");
        }
        return JSONResult.success("保存成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequiredPermission("员工删除")
    public JSONResult delete(Long id) {
        try {
            employeeService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("员工删除成功");
    }

    @RequestMapping("batchDelete")
    @ResponseBody
    @RequiredPermission("员工批量删除")
    public JSONResult batchDelete(@RequestParam("ids[]") Long[] ids) {
        System.out.println(Arrays.toString(ids));
        try {
            employeeService.batchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("删除成功");
    }


}
