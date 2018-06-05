package com.hyl.wms.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Set;

public class SessionUtil {
    private SessionUtil() {
    };

    private static final String USER_IN_SESSION = "USER_IN_SESSION";
    private static final String EXPRESSION_IN_SESSION = "EXPRESSION_IN_SESSION";


    public static HttpSession getSession() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest().getSession();
    }

    public static void setCurrentUserMsg(Object object) {
        if (object == null) {
            //注销
            getSession().invalidate();
        } else {
            getSession().setAttribute(USER_IN_SESSION, object);
        }
    }

    public static void setCurrentUserPermission(Set<String> expressions) {
        getSession().setAttribute(EXPRESSION_IN_SESSION, expressions);
    }

    public static Object getCurrentUserMsg() {
        return getSession().getAttribute(USER_IN_SESSION);
    }

    public static Set<String> getCurrentUserPermission() {
        return (Set<String>) getSession().getAttribute(EXPRESSION_IN_SESSION);
    }

}
