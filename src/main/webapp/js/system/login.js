$(document).ready(function() {
	$('#pwd').keypress(enterLogin).keydown(enterLogin);
});

function enterLogin(e) { // 传入 event
	var e = e || window.event;
    if (e.keyCode == 13) {
        login();
	}
}

$(function () {
    $("#login_sub").click(function() {
       login();
    });
});

function login() {
    $("#submitForm").ajaxSubmit(function (data) {
        if(data.success) {
            $.artDialog({
                title: "提示",
                content: data.msg,
                icon: "face-smile",
                ok: function () {
                    window.location.href = "/main.do";
                }
            })
        } else {
            $("#login_err").text(data.msg);
        }
    });
}