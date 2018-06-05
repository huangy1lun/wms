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
            // $("#editForm :input").prop("disabled", true);
            $(".backView").click(function () {
                window.history.back();
            })
        })
    </script>
</head>
<body>
<form name="editForm" action="/orderbill/saveOrUpdate.do" method="post" id="editForm">
    <input type="hidden" name="id" value="${orderbill.id}" disabled/>
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">采购订单查看</span>
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
                        <input type="text" name="sn" value="${orderbill.sn}" class="ui_input_txt02" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">供应商</td>
                    <td class="ui_text_lt">
                        <select name="supplier.id" class="ui_select01" disabled>
                            <c:forEach items="${suppliers}" var="item">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">业务时间</td>
                    <td class="ui_text_lt">
                        <input name="vdate" value="${orderbill.formatVdate}" class="ui_input_txt02 Wdate" disabled
                               onClick="WdatePicker()"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">单据明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
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
                            </tr>
                            </thead>
                            <tbody id="edit_table_body">

                            <c:forEach items="${orderbill.items}" var="item">
                                <tr>
                                    <td></td>
                                    <td>
                                        <input disabled="true" readonly="true" class="ui_input_txt02" tag="name"
                                               value="${item.product.name}"/>
                                        <input type="hidden" name="" tag="pid" value="${item.product.id}"/>
                                    </td>
                                    <td><span tag="brand">${item.product.brandName}</span></td>
                                    <td><input tag="costPrice" name="" value="${item.costPrice}" disabled
                                               class="ui_input_txt00"/></td>
                                    <td><input tag="number" name="" value="${item.number}" disabled
                                               class="ui_input_txt00"/></td>
                                    <td><span tag="amount">${item.amount}</span></td>
                                    <td><input tag="remark" name="" value="${item.remark}" disabled
                                               class="ui_input_txt02"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>

                        &nbsp;<input type="button"  value="返回" class="ui_input_btn01 backView" />
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>