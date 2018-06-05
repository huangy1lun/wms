<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <link href="/js/plugins/fancyBox/source/jquery.fancybox.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery.form.min.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue "></script>
    <script src="/js/plugins/fancyBox/source/jquery.fancybox.js"></script>
    <script>
        $(function () {
            $('.fancybox').fancybox();

            $("#editForm").ajaxForm(function (data) {
                if(data.success) {
                    $.artDialog({
                        title: "提示",
                        content: data.msg,
                        icon: "face-smile",
                        ok: function () {
                            window.location.href = "/product/list.do";
                        }
                    })
                } else {
                    $.artDialog({
                        title: "提示",
                        content: data.msg,
                        icon: "face-sad",
                        ok: true
                    })
                }

            })
        })
    </script>
</head>
<body>
<form name="editForm" action="/product/saveOrUpdate.do" method="post" id="editForm" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${product.id}"/>
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">商品编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">商品编码</td>
                    <td class="ui_text_lt">
                        <input name="sn" value="${product.sn}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">商品名称</td>
                    <td class="ui_text_lt">
                        <input type="text" name="name" value="${product.name}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">成本价格</td>
                    <td class="ui_text_lt">
                        <input name="costPrice" value="${product.costPrice}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">销售价格</td>
                    <td class="ui_text_lt">
                        <input name="salePrice" value="${product.salePrice}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">商品品牌</td>
                    <td class="ui_text_lt">
                        <select name="brandId" class="ui_select03">
                            <c:forEach items="${brands}" var="brand">
                                <option ${product.brandId==brand.id ? 'selected':''} value="${brand.id}">${brand.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">商品图片</td>
                    <td class="ui_text_lt">
                        <input name="img" class="ui_file" type="file"/>
                        <a class="fancybox" href="${product.imagePath}" title="${product.intro}">
                            <img src="${product.smallImagePath}" width="100px"/>
                        </a>
                        <%--使用隐藏域将图片路径保存起来,然后传递到后台使用--%>
                        <input type="hidden" name="imagePath" value="${product.imagePath}"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">商品简介</td>
                    <td class="ui_text_lt">
                        <textarea name="intro" class="ui_input_txtarea">${product.intro}</textarea>
                    </td>
                </tr>

                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<input type="submit" value="确定保存" class="ui_input_btn01"/>
                        &nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    </form>
</body>
</html>