<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fwt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <script type="text/javascript" src="/js/plugins/datePicker/WdatePicker.js"></script>
    <title>PSS-账户管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
    <script>
        $(function () {
            //审批操作
            $(".audit_a").click(function () {
                var url = $(this).data("url").split("?")[0];
                var json = parseUrl($(this).data("url"));
                console.log();
                $.artDialog({
                    title: "提示",
                    content: "是否确定通过此条订单 ?",
                    icon: "warning",
                    ok: function () {
                        $.post(url, json, function (data) {
                            responseControl(data);
                        })
                    },
                    cancel: true
                })
            });

            //datePiker 约束选择时间
            $(".beginDate").click(function () {
                WdatePicker({
                    maxDate: $(".endDate").val()
                });
            });
            $(".endDate").click(function () {
                WdatePicker({
                    minDate: $(".beginDate").val()
                });
            });

        })
    </script>
</head>
<body>
<form id="searchForm" action="/stockincomebill/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        开始时间
                        <fwt:formatDate value="${qo.beginDate}" var="beginDate"/>
                        <input type="text" class="ui_input_txt02 Wdate beginDate" name="beginDate" value="${beginDate}"/>
                        结束时间
                        <fwt:formatDate value="${qo.endDate}" var="endDate"/>
                        <input type="text" class="ui_input_txt02 Wdate endDate" name="endDate" value="${endDate}"/>
                        仓库
                        <select class="ui_select01" name="depotId">
                            <option value="-1">--请选择--</option>
                            <c:forEach items="${depots}" var="item">
                                <option value="${item.id}" ${item.id == qo.depotId ? 'selected':''}>${item.name}</option>
                            </c:forEach>
                        </select>
                        审核状态
                        <select name="status" id="aduitStatus" class="ui_select01">
                            <option value="-1">--请选择--</option>
                            <option value="0">未审核</option>
                            <option value="1">已审核</option>
                        </select>
                        <script>
                            $("#aduitStatus option[value=${qo.status}]").prop("selected", true);
                        </script>
                    </div>
                    <div id="box_bottom">
                        <input type="submit" value="查询" class="ui_input_btn01"/>
                        <input type="button" value="新增" class="ui_input_btn01 btn_input btn_redirect"
                               data-url="/stockincomebill/input.do"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>订单编号</th>
                        <th>业务时间</th>
                        <th>仓库</th>
                        <th>总金额</th>
                        <th>总数量</th>
                        <th>录入人</th>
                        <th>审核人</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${result.list}" var="item">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb"/></td>
                            <td>${item.sn}</td>
                            <td>${item.formatVdate}</td>
                            <td>${item.depot.name}</td>
                            <td>${item.totalAmount}</td>
                            <td>${item.totalNumber}</td>
                            <td>${item.inputUser.name}</td>
                            <td>${item.auditor.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.status == 0}">
                                        <span style="color:red">未审核</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: green">已审核</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.status == 0}">
                                        <a href="/stockincomebill/input.do?id=${item.id}">编辑</a>
                                        <a href="javascript:;" class="audit_a"
                                           data-url="/stockincomebill/audit.do?id=${item.id}">审核</a>
                                        <a href="javascript:;" class="delete_a"
                                           data-url="/stockincomebill/delete.do?id=${item.id}">删除</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="/stockincomebill/view.do?id=${item.id}">查看</a>
                                    </c:otherwise>
                                </c:choose>
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