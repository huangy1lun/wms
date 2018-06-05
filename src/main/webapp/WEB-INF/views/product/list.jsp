<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <link href="/js/plugins/fancyBox/source/jquery.fancybox.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script src="/js/plugins/fancyBox/source/jquery.fancybox.js"></script>
    <title>PSS-账户管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
    <script>
        $('.fancybox').fancybox();
    </script>
</head>
<body>
<form id="searchForm" action="/product/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        商品名称/品牌
                        <input type="text" class="ui_input_txt02" name="keyword" value="${qo.keyword}"/>
                        所属品牌
                        <select class="ui_select01" name="brandId">
                            <option value="-1">--请选择--</option>
                            <c:forEach items="${brands}" var="item">
                                <option value="${item.id}" ${item.id == qo.brandId ? 'selected':''}>${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="box_bottom">
                        <input type="submit" value="查询" class="ui_input_btn01"/>
                        <input type="button" value="新增" class="ui_input_btn01 btn_input btn_redirect"
                        data-url="/product/input.do"/>
                        <input type="button" value="批量删除" class="ui_input_btn01 btn_input btn_batchDelete"
                               data-url="/product/batchDelete.do" />
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>商品图片</th>
                        <th>商品名称</th>
                        <th>商品编码</th>
                        <th>商品品牌</th>
                        <th>商品成本价</th>
                        <th>商品销售价</th>
                        <th>操作</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${result.list}" var="item">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb"  value="${item.id}"/></td>
                            <td>
                                <a class="fancybox" href="${item.imagePath}" title="${item.intro}">
                                    <img class="list_img_min" src="${item.smallImagePath}" alt="" />
                                </a>
                            </td>
                            <td>${item.name}</td>
                            <td>${item.sn}</td>
                            <td>${item.brandName}</td>
                            <td>${item.costPrice}</td>
                            <td>${item.salePrice}</td>
                            <td>
                                <a href="/product/input.do?id=${item.id}">编辑</a>
                                <a href="javascript:;" class="delete_a" data-url="/product/delete.do?id=${item.id}&imagePath=${item.imagePath}">删除</a>
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