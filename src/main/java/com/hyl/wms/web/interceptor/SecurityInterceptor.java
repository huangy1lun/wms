package com.hyl.wms.web.interceptor;

import com.hyl.wms.domain.Employee;
import com.hyl.wms.exception.SecurityException;
import com.hyl.wms.util.RequiredPermission;
import com.hyl.wms.util.SessionUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取登录用户信息
        Employee employee = (Employee) SessionUtil.getCurrentUserMsg();
        //判断是否是超级管理员
        if(employee.isAdmin()) {
            return true;
        }
        //获取当前用户的所有权限
        Set<String> userPermission = SessionUtil.getCurrentUserPermission();
        //获取当前访问的Controller和方法,连接成权限表达式
        HandlerMethod hm = (HandlerMethod) handler;
        if("com.hyl.wms.web.controller.PermissionController".equals(hm.getBean().getClass().getName())) {
            throw new SecurityException("没有权限访问当前资源");
        }
        //判断是否有注解
        if(!hm.getMethod().isAnnotationPresent(RequiredPermission.class)) {
            return true;
        }
        //如果有注解,判断权限表达式是否包含
        String expression = hm.getBean().getClass().getName() + ":" + hm.getMethod().getName();
        if (userPermission.contains(expression)) {
            return true;
        }
        throw new SecurityException("没有权限访问当前资源");
    }
}
