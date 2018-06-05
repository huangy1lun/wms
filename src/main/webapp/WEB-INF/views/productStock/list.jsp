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
    <title>PSS-账户管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/productStock/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        货品名称或编号
                        <input type="text" class="ui_input_txt02" name="keyword" value="${qo.keyword}"/>
                        所在仓库
                        <select class="ui_select01" name="depotId">
                            <option value="-1">--所有仓库--</option>
                            <c:forEach items="${depots}" var="item">
                                <option value="${item.id}" ${item.id == qo.depotId ? 'selected':''}>${item.name}</option>
                            </c:forEach>
                        </select>
                        货品品牌
                        <select class="ui_select01" name="brandId">
                            <option value="-1">--所有品牌--</option>
                            <c:forEach items="${brands}" var="item">
                                <option value="${item.id}" ${item.id == qo.brandId ? 'selected':''}>${item.name}</option>
                            </c:forEach>
                        </select>
                        库存阈值
                        <input type="text" class="ui_input_txt02" name="limitNumber" value="${qo.limitNumber}"/>
                    </div>
                    <div id="box_bottom">
                        <input type="submit" value="查询" class="ui_input_btn01"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th>仓库</th>
                        <th>商品编码</th>
                        <th>商品名称</th>
                        <th>品牌</th>
                        <th>库存数量</th>
                        <th>成本</th>
                        <th>库存汇总</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${result.list}" var="item">
                        <tr>
                            <td>${item.depot.name}</td>
                            <td>${item.product.sn}</td>
                            <td>${item.product.name}</td>
                            <td>${item.product.brandName}</td>
                            <td>${item.storeNumber}</td>
                            <td>${item.price}</td>
                            <td>${item.amount}</td>
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