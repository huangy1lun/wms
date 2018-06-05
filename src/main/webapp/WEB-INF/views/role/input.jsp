<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/system/role.js"></script>
    <script>
        $(function () {
            //处理表单提交前全部option选中
            $("#editForm").submit(function () {
                $("#self_permission option").each(function (index, item) {
                    $(item).prop("selected",true);
                });
                $("#self_menus option").each(function (index, item) {
                    $(item).prop("selected",true);
                });
            });
            //使用ajax提交表单
            $("#editForm").ajaxForm(function (data) {
                if(data.success) {
                    $.artDialog({
                        title: "提示",
                        content: data.msg,
                        icon: "face-smile",
                        ok: function () {
                            window.location.href = "/role/list.do";
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
<form name="editForm" action="/role/saveOrUpdate.do" method="post" id="editForm">
    <input type="hidden" name="id" value="${role.id}"/>
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">角色编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">角色名称</td>
                    <td class="ui_text_lt">
                        <input name="name" value="${role.name}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">角色描述</td>
                    <td class="ui_text_lt">
                        <input type="text" name="sn" value="${role.sn}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">权限</td>
                    <td class="ui_text_lt">
                        <table>
                            <tr>
                                <td>
                                    <select multiple="true" class="ui_multiselect01" id="all_permission">
                                        <c:forEach items="${permissions}" var="item">
                                            <option value="${item.id}">${item.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td align="center">
                                    <input type="button" id="select" value="-->" class="left2right"/><br/>
                                    <input type="button" id="selectAll" value="==>" class="left2right"/><br/>
                                    <input type="button" id="deselect" value="<--" class="left2right"/><br/>
                                    <input type="button" id="deselectAll" value="<==" class="left2right"/>
                                </td>
                                <td>
                                    <select multiple="true" class="ui_multiselect01" id="self_permission" name="ids">
                                        <c:forEach items="${role.permissionList}" var="item">
                                            <option value="${item.id}">${item.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">菜单</td>
                    <td class="ui_text_lt">
                        <table>
                            <tr>
                                <td>
                                    <select multiple="true" class="ui_multiselect01" id="all_menus">
                                        <c:forEach items="${menus}" var="item">
                                            <option value="${item.id}">${item.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td align="center">
                                    <input type="button" id="mSelect" value="-->" class="left2right"/><br/>
                                    <input type="button" id="mSelectAll" value="==>" class="left2right"/><br/>
                                    <input type="button" id="mDeselect" value="<--" class="left2right"/><br/>
                                    <input type="button" id="mDeselectAll" value="<==" class="left2right"/>
                                </td>
                                <td>
                                    <select multiple="true" class="ui_multiselect01" id="self_menus" name="menuIds">
                                        <c:forEach items="${role.menuList}" var="item">
                                            <option value="${item.id}">${item.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
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