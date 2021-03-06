
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery.form.min.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue "></script>
    <script>
        $(function () {
            $("#editForm").ajaxForm(function (data) {
                if(data.success) {
                    $.artDialog({
                        title: "提示",
                        content: data.msg,
                        icon: "face-smile",
                        ok: function () {
                            window.location.href = "/systemmenu/list.do?parentId=${parentId}";
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
<form name="editForm" action="/systemmenu/saveOrUpdate.do" method="post" id="editForm">
    <input type="hidden" name="id" value="${systemmenu.id}" />
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">菜单编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">父级菜单</td>
                    <td class="ui_text_lt">
                        <input value="${parentMenu}" readonly disabled class="ui_input_txt02"/>
                        <input type="hidden" name="parent.id" value="${parentId}"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">菜单名称</td>
                    <td class="ui_text_lt">
                        <input name="name" value="${systemmenu.name}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">菜单编号</td>
                    <td class="ui_text_lt">
                        <input type="text" name="sn" value="${systemmenu.sn}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">URL</td>
                    <td class="ui_text_lt">
                        <input type="text" name="url" value="${systemmenu.url}" class="ui_input_txt02"/>
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