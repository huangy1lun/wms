<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: xiaohai
  Date: 2018/6/6
  Time: 22:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<shiro:notAuthenticated>
    亲,请登录!
</shiro:notAuthenticated>
<shiro:authenticated>
    <shiro:principal property="name"/>
</shiro:authenticated>
</body>
</html>
