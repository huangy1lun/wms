<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue "></script>
    <script type="text/javascript" src="/js/system/employee.js"></script>
    <script src="/js/plugins/jquery.validate.js"></script>
</head>

<script>
    $(function () {
        //处理表单提交前全部option选中
        $("#editForm").submit(function () {
            $("#self_roles option").each(function (index, item) {
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
                        window.location.href = "/employee/list.do";
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
        // $("#editForm").validate({
        //     rules: {
        //         name: {required: true, minlength: 2},
        //         password: {required: true, minlength: 5},
        //         confirm_password: {required: true, minlength: 5, equalTo: "#password"},
        //         email: {required: true, email: true},
        //         age:{required:true,digits:true,range:[10,80]}
        //     },
        //     messages: {
        //         name:{required:"请输入用户名", minlength: "用户名必需由两个字母组成"},
        //         password: {required: "请输入密码", minlength: "密码长度不能小于 {0} 个字母"},
        //         confirm_password: {required: "请输入密码", minlength: "密码长度不能小于 {0} 个字母", equalTo: "两次密码输入不一致"},
        //         email: "请输入一个正确的邮箱",
        //         age:{required:"请输入年龄",digits:"年龄必须是整数",range:"年龄必须在{0}到{1}之间"}
        //     }
        // });
    })
</script>
<body>
<form name="editForm" action="/employee/saveOrUpdate.do" method="post" id="editForm">
    <input type="hidden" name="id" value="${employee.id}"/>
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">用户编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">用户名</td>
                    <td class="ui_text_lt">
                        <input name="name" value="${employee.name}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <c:if test="${employee == null}">
                    <tr>
                        <td class="ui_text_rt" width="140">密码</td>
                        <td class="ui_text_lt">
                            <input type="password" name="password" id="password" class="ui_input_txt02"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="ui_text_rt" width="140">验证密码</td>
                        <td class="ui_text_lt">
                            <input id="confirmPassword" name="confirm_password" type="password" class="ui_input_txt02"/>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td class="ui_text_rt" width="140">Email</td>
                    <td class="ui_text_lt">
                        <input name="email" value="${employee.email}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">年龄</td>
                    <td class="ui_text_lt">
                        <input name="age" value="${employee.age}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">所属部门</td>
                    <td class="ui_text_lt">
                        <select name="dept.id" class="ui_select01">
                            <option value="-1">--请选择--</option>
                            <c:forEach items="${departments}" var="dept">
                                <option value="${dept.id}" ${dept.id == employee.dept.id ? 'selected':''}>${dept.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">超级管理员</td>
                    <td class="ui_text_lt">
                        <input type="checkbox" name="admin" class="ui_checkbox01" />
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">角色</td>
                    <td class="ui_text_lt">
                        <table>
                            <tr>
                                <td>
                                    <select multiple="true" class="ui_multiselect01" id="all_roles">
                                        <c:forEach items="${roles}" var="item">
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
                                    <select multiple="true" class="ui_multiselect01" id="self_roles" name="ids">
                                        <c:forEach items="${employee.roleList}" var="item">
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