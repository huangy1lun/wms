package com.hyl.wms.web.interceptor;

import com.hyl.wms.domain.Employee;
import com.hyl.wms.util.SessionUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class checkLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Employee userMsg = (Employee) SessionUtil.getCurrentUserMsg();
        //判断session中是否有用户信息
        if(userMsg != null) {
            return true;
        }
        response.sendRedirect("/login.html");
        return false;
    }
}
