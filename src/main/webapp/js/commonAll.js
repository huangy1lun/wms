/** table鼠标悬停换色* */
$(function() {
	// 如果鼠标移到行上时，执行函数
	$(".table tr").mouseover(function() {
		$(this).css({background : "#CDDAEB"});
		$(this).children('td').each(function(index, ele){
			$(ele).css({color: "#1D1E21"});
		});
	}).mouseout(function() {
		$(this).css({background : "#FFF"});
		$(this).children('td').each(function(index, ele){
			$(ele).css({color: "#909090"});
		});
	});
});

$(function () {
    //全选/全不选
    $("#all").click(function () {
       $(".acb").prop("checked",this.checked);
    })
})

$(function () {
    //页面跳转
	$(".btn_redirect").click(function () {
        window.location.href = $(this).data("url");
    })

    //删除
    $(".delete_a").click(function () {
        var url = $(this).data("url").split("?")[0];
        var json = parseUrl($(this).data("url"));
        console.log();
        $.artDialog({
            title:"提示",
            content:"是否确定删除该条数据 ?",
            icon:"warning",
            ok:function () {
                $.post(url,json,function (data) {
                    responseControl(data);
                })
            },
            cancel:true
        })
    });


	//批量删除
    $(".btn_batchDelete").click(function () {
        var checkedArr = [];
        $(".acb:checked").each(function (index,item) {
            checkedArr.push($(item).val());
        })
        //判断是否有选中
        if(checkedArr.length == 0){
            $.artDialog({
                title:"提示",
                content:"请选择至少一条数据",
                icon:"warning",
                ok:true
            });
            return;
        }
        var url = $(this).data("url");
        //删除数据
        $.artDialog({
            title:"提示",
            content:"是否确定删除多条数据 ?",
            icon:"warning",
            ok:function () {
                $.post(url,{ids:checkedArr},function (data) {
                    responseControl(data);
                })
            },
            cancel:true
        });
    })
});
//ajax结果处理
function responseControl(data) {
    if(data.success){
        $.artDialog({
            title: "提示",
            content: data.msg,
            ok:function () {
                window.location.reload();
            }
        });

    } else {
        $.artDialog({
            title: "提示",
            content: data.msg,
            ok:true
        });
    }
}
//解析url 以json返回参数
function parseUrl(url) {
    var json = url.split("?")[1];
    var arr = json.split("&");
    var obj = {};
    for(var i = 0 ; i < arr.length; i++) {
        obj[arr[i].split("=")[0]] = arr[i].split("=")[1];
    }
    return obj;
}

