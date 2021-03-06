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
    <script type="text/javascript" src="/js/plugins/artDialog/plugins/iframeTools.js"></script>
    <title>PSS-账户管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
    <script>
        $(function () {
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

            $(".btn_chart").click(function () {
                var url = $(this).data("url") +"?"+$("#searchForm").serialize();
                $.dialog.open(url, {
                    title: "选择商品",
                    width: 1000,
                    height: 600,
                }, false);
            })
        })
    </script>
</head>
<body>
<form id="searchForm" action="/chart/sale.do" method="post">
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
                        客户
                        <select class="ui_select01" name="">
                            <option value="-1">--请选择--</option>
                            <c:forEach items="${clients}" var="item">
                                <option value="${item.id}" ${item.id == qo.clientId ? 'selected':''}>${item.name}</option>
                            </c:forEach>
                        </select>
                        货品品牌
                        <select class="ui_select01" name="brandId">
                            <option value="-1">--所有品牌--</option>
                            <c:forEach items="${brands}" var="item">
                                <option value="${item.id}" ${item.id == qo.brandId ? 'selected':''}>${item.name}</option>
                            </c:forEach>
                        </select>
                        分组
                        <select class="ui_select01" name="groupByType">
                            <c:forEach items="${groupByTypes}" var="item">
                                <option value="${item.key}" ${item.key == qo.groupByType ? 'selected':''}>${item.value}</option>
                            </c:forEach>
                        </select>
                        <input type="submit" value="查询" class="ui_input_btn01"/>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="柱状图" class="left2right btn_chart" data-url="/chart/bar.do"/>
                        <input type="button" value="饼状图" class="left2right btn_chart" data-url="/chart/pie.do"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th>分组类型</th>
                        <th>采购总数量</th>
                        <th>采购总金额</th>
                        <th>利润</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${list}" var="item">
                        <tr>
                            <td>${item.groupByType}</td>
                            <td>${item.totalNumber}</td>
                            <td>${item.totalAmount}</td>
                            <td>${item.grossProfit}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</form>
</body>
</html>