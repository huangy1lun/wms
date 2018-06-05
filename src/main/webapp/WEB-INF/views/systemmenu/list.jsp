<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>PSS-账户管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/systemmenu/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_bottom">
                        <input type="button" value="新增" class="ui_input_btn01 btn_input btn_redirect"
                        data-url="/systemmenu/input.do?parentId=${qo.parentId}"/>
                    </div>
                </div>
                当前目录: <a href="/systemmenu/list.do">根目录</a>
                <c:forEach items="${mentList}" var="item">
                    > <a href="/systemmenu/list.do?parentId=${item.id}">${item.name}</a>
                </c:forEach>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>ID</th>
                        <th>父级菜单</th>
                        <th>菜单名称</th>
                        <th>菜单编号</th>
                        <th>URL</th>
                        <th>操作</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${result.list}" var="item">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb"/></td>
                            <td>${item.id}</td>
                            <td>${item.parent.name}</td>
                            <td>${item.name}</td>
                            <td>${item.sn}</td>
                            <td>${item.url}</td>
                            <td>
                                <a href="/systemmenu/input.do?id=${item.id}&parentId=${item.parent.id}">编辑</a>
                                <a href="javascript:;" class="delete_a" data-url="/systemmenu/delete.do?id=${item.id}">删除</a>
                                <a href="/systemmenu/list.do?parentId=${item.id}" >查看下级菜单</a>
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