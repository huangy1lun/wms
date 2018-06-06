package com.hyl.wms.web.controller;

import com.hyl.wms.service.IEmployeeService;
import com.hyl.wms.util.SessionUtil;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping("login")
    public String login(HttpServletRequest request,Map map) {
        Object shiroLoginFailure = request.getAttribute("shiroLoginFailure");
        String errorMsg = "";
        if (shiroLoginFailure != null) {
            if(shiroLoginFailure.equals(UnknownAccountException.class.getName())) {
                errorMsg = "用户名不存在";
            } else if(shiroLoginFailure.equals(IncorrectCredentialsException.class.getName())) {
                errorMsg = "密码错误";
            }
            map.put("errorMsg",errorMsg);
        }
        return "forward:/login.jsp";
    }

//    @RequestMapping("login")
//    @ResponseBody
//    public JSONResult login(Employee employee) {
//        try {
//            employeeService.checkLogin(employee);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return JSONResult.mark(e.getMessage());
//        }
//        return JSONResult.success("登录成功");
//    }

//    @RequestMapping("logout")
//    public String logout() {
//        SessionUtil.setCurrentUserMsg(null);
//        return "redirect:/login.html";
//    }
}
