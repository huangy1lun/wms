<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery.form.min.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/plugins/iframeTools.js"></script>
    <script type="text/javascript" src="/js/plugins/datePicker/WdatePicker.js"></script>
    <script>
        $(function () {
            //点击添加商品
            $("#edit_table_body").on("click", ".searchproduct", function () {
                var tr = $(this).closest("tr");
                //模态框
                $.dialog.open("/product/selectByOrder.do", {
                    title: "选择商品",
                    width: 1000,
                    height: 680,
                    close: function () {
                        var json = $.dialog.data("json"); // 读取子窗口返回的数据
                        console.log(json);
                        if (json) {
                            tr.find("[tag=pid]").val(json.id);
                            tr.find("[tag=name]").val(json.name);
                            tr.find("[tag=costPrice]").val(json.costPrice);
                            tr.find("[tag=brand]").text(json.brandName);
                            $("[tag=costPrice]").change();
                        }
                    }
                }, false);

            });

            //计算明细小计
            $("#edit_table_body").on("change", "[tag=costPrice],[tag=number]", function () {
                var tr = $(this).closest("tr");
                var price = parseFloat(tr.find("[tag=costPrice]").val() || 0);
                var count = parseFloat(tr.find("[tag=number]").val() || 0);
                tr.find("[tag=amount]").text((price * count).toFixed(2));
            });

            //添加明细
            $(".appendRow").click(function () {
                var tr = $("#edit_table_body tr:first").clone();
                tr.find("input").val("");
                tr.find("span").text("");
                tr.appendTo("#edit_table_body");
            });

            //删除明细
            $("#edit_table_body").on("click",".removeItem",function () {
                var tr = $(this).closest("tr");
                if ($("#edit_table_body tr").length > 1) {
                    tr.remove();
                } else {
                    tr.find("input").val("");
                    tr.find("span").text("");
                }

            })


            $("#editForm").submit(function () {
                var trs = $("#edit_table_body tr");
                for (var i = 0; i < trs.length; i++) {
                    var tr = $(trs[i]);
                    tr.find("[tag=pid]").prop("name", "items[" + i + "].product.id");
                    tr.find("[tag=costPrice]").prop("name", "items[" + i + "].costPrice");
                    tr.find("[tag=number]").prop("name", "items[" + i + "].number");
                    tr.find("[tag=remark]").prop("name", "items[" + i + "].remark");
                }
            });

            //提交表单
            $("#editForm").ajaxForm(function (data) {
                if (data.success) {
                    $.artDialog({
                        title: "提示",
                        content: data.msg,
                        icon: "face-smile",
                        ok: function () {
                            window.location.href = "/stockincomebill/list.do";
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
            });

        })
    </script>
</head>
<body>
<form name="editForm" action="/stockincomebill/saveOrUpdate.do" method="post" id="editForm">
    <input type="hidden" name="id" value="${stockincomebill.id}"/>
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">入库订单编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">订单编号</td>
                    <td class="ui_text_lt">
                        <input type="text" name="sn" value="${stockincomebill.sn}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">仓库</td>
                    <td class="ui_text_lt">
                        <select name="depot.id" class="ui_select01">
                            <c:forEach items="${depots}" var="item" >
                                <option value="${item.id}" ${item.id == stockincomebill.depot.id ? "selected":""}>${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">业务时间</td>
                    <td class="ui_text_lt">
                        <%--<fmt:formatDate value="${stockincomebill.vdate}" pattern="yyyy-MM-dd" var="formatDate"/>--%>
                        <input name="vdate" value="${stockincomebill.formatVdate}" class="ui_input_txt02 Wdate"  onClick="WdatePicker()"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">单据明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="button" value="添加明细" class="ui_input_btn01 appendRow"/>
                        <table class="edit_table" cellspacing="0" cellpadding="0" border="0" style="width: auto">
                            <thead>
                            <tr>
                                <th width="10"></th>
                                <th width="200">货品</th>
                                <th width="120">品牌</th>
                                <th width="80">价格</th>
                                <th width="80">数量</th>
                                <th width="80">金额小计</th>
                                <th width="150">备注</th>
                                <th width="60"></th>
                            </tr>
                            </thead>
                            <tbody id="edit_table_body">
                            <c:choose>
                                <c:when test="${stockincomebill == null}">
                                    <tr>
                                        <td></td>
                                        <td>
                                            <input disabled="true" readonly="true" class="ui_input_txt02" tag="name"/>
                                            <img src="/images/common/search.png" class="searchproduct"/>
                                            <input type="hidden" name="" tag="pid"/>
                                        </td>
                                        <td><span tag="brand">${item.produt.brandName}</span></td>
                                        <td><input tag="costPrice" name=""
                                                   class="ui_input_txt00"/></td>
                                        <td><input tag="number" name=""
                                                   class="ui_input_txt00"/></td>
                                        <td><span tag="amount"></span></td>
                                        <td><input tag="remark" name=""
                                                   class="ui_input_txt02"/></td>
                                        <td>
                                            <a href="javascript:;" class="removeItem">删除明细</a>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${stockincomebill.items}" var="item">
                                        <tr>
                                            <td></td>
                                            <td>
                                                <input disabled="true" readonly="true" class="ui_input_txt02" tag="name" value="${item.product.name}"/>
                                                <img src="/images/common/search.png" class="searchproduct"/>
                                                <input type="hidden" name="" tag="pid" value="${item.product.id}"/>
                                            </td>
                                            <td><span tag="brand">${item.product.brandName}</span></td>
                                            <td><input tag="costPrice" name="" value="${item.costPrice}"
                                                       class="ui_input_txt00"/></td>
                                            <td><input tag="number" name="" value="${item.number}"
                                                       class="ui_input_txt00"/></td>
                                            <td><span tag="amount">${item.amount}</span></td>
                                            <td><input tag="remark" name="" value="${item.remark}"
                                                       class="ui_input_txt02"/></td>
                                            <td>
                                                <a href="javascript:;" class="removeItem">删除明细</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                            </tbody>
                        </table>
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