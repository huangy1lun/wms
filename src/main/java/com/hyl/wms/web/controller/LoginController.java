package com.hyl.wms.web.controller;

import com.hyl.wms.domain.Employee;
import com.hyl.wms.service.IEmployeeService;
import com.hyl.wms.util.SessionUtil;
import com.hyl.wms.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping("login")
    @ResponseBody
    public JSONResult login(Employee employee) {
        try {
            employeeService.checkLogin(employee);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.mark(e.getMessage());
        }
        return JSONResult.success("登录成功");
    }

    @RequestMapping("logout")
    public String logout() {
        SessionUtil.setCurrentUserMsg(null);
        return "redirect:/login.html";
    }
}
