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
    <script>
        $(function () {
            $(".btn_load").click(function () {
                $.artDialog({
                    title: "提示",
                    content: "加载操作比较耗时,你确定要加载吗 ?",
                    icon: "face-smile",
                    ok: function () {
                        $.post("/permission/load.do",function(data){
                            var loading = $.artDialog({
                                title: "提示"
                            });
                            if(data.success) {
                                loading.close();
                                $.artDialog({
                                    title: "提示",
                                    content: data.msg,
                                    icon: "face-smile",
                                    ok: function () {
                                        window.location.href = "/permission/list.do";
                                    }
                                })
                            } else {
                                loading.close();
                                $.artDialog({
                                    title: "提示",
                                    content: data.msg,
                                    icon: "face-sad",
                                    ok:true
                                })
                            }
                        })
                    },
                    cancel:true
                })
            })
        })
    </script>
    <title>PSS-账户管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/permission/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_bottom">
                        <input type="button" value="加载权限" class="ui_input_btn01 btn_input btn_load"/>
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
                        <th>权限名称</th>
                        <th>权限表达式</th>
                        <th>操作</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${result.list}" var="item">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb"/></td>
                            <td>${item.id}</td>
                            <td>${item.name}</td>
                            <td>${item.expression}</td>
                            <td>
                                <a href="javascript:;" class="delete_a" data-url="/permission/delete.do?id=${item.id}">删除</a>
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