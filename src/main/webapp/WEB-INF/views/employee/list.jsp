<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <title>叩丁狼教育PSS（演示版）-账户管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/employee/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        姓名/邮箱
                        <input type="text" class="ui_input_txt02" name="keyword" value="${qo.keyword}"/>
                        所属部门
                        <select class="ui_select01" name="deptId">
                            <option value="-1">--请选择--</option>
                            <c:forEach items="${departments}" var="dept">
                                <option value="${dept.id}" ${dept.id == qo.deptId ? 'selected':''}>${dept.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="box_bottom">
                        <input type="submit" value="查询" class="ui_input_btn01"/>
                        <input type="button" value="新增" class="ui_input_btn01 btn_input btn_redirect"
                               data-url="/employee/input.do"/>
                        <input type="button" value="批量删除" class="ui_input_btn01 btn_input btn_batchDelete"
                               data-url="/employee/batchDelete.do" />
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>编号</th>
                        <th>用户名</th>
                        <th>EMAIL</th>
                        <th>年龄</th>
                        <th>所属部门</th>
                        <th>角色</th>
                        <th>操作</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${result.list}" var="item">
                        <tr>
                                <td><input type="checkbox" name="IDCheck" class="acb" value="${item.id}"/></td>
                            <td>${item.id}</td>
                            <td>${item.name}</td>
                            <td>${item.email}</td>
                            <td>${item.age}</td>
                            <td>${item.dept.name}</td>
                            <td>${item.roleNames}</td>
                            <td>
                                <shiro:hasPermission name="cn.wolfcode.wms.web.controller.EmployeeController:delete">
                                <a href="/employee/input.do?id=${item.id}">编辑</a>
                                <a href="javascript:;" class="delete_a" data-url="/employee/delete.do?id=${item.id}">删除</a>
                                </shiro:hasPermission>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <%--分页--%>
            <jsp:include page="../commons/common_page.jsp"/>
        </div>
    </div>
</form>
</body>
</html>
