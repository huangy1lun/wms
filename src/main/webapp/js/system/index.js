//加载当前日期
function loadDate() {
    var time = new Date();
    var myYear = time.getFullYear();
    var myMonth = time.getMonth() + 1;
    var myDay = time.getDate();
    if (myMonth < 10) {
        myMonth = "0" + myMonth;
    }
    document.getElementById("day_day").innerHTML = myYear + "." + myMonth + "." + myDay;
}

/**
 * 隐藏或者显示侧边栏
 *
 **/
function switchSysBar(flag) {
    var side = $('#side');
    var left_menu_cnt = $('#left_menu_cnt');
    if (flag == true) {	// flag==true
        left_menu_cnt.show(500, 'linear');
        side.css({width: '280px'});
        $('#top_nav').css({width: '77%', left: '304px'});
        $('#main').css({left: '280px'});
    } else {
        if (left_menu_cnt.is(":visible")) {
            left_menu_cnt.hide(10, 'linear');
            side.css({width: '60px'});
            $('#top_nav').css({width: '100%', left: '60px', 'padding-left': '28px'});
            $('#main').css({left: '60px'});
            $("#show_hide_btn").find('img').attr('src', 'images/common/nav_show.png');
        } else {
            left_menu_cnt.show(500, 'linear');
            side.css({width: '280px'});
            $('#top_nav').css({width: '77%', left: '304px', 'padding-left': '0px'});
            $('#main').css({left: '280px'});
            $("#show_hide_btn").find('img').attr('src', 'images/common/nav_hide.png');
        }
    }
}

$(function () {
    loadDate();

    // 显示隐藏侧边栏
    $("#show_hide_btn").click(function () {
        switchSysBar();
    });

    //点击切换左边菜单
    $("#TabPage2 li").click(function () {

        $("#TabPage2 li").each(function (index, item) {
            //取消全部选中效果
            $(item).removeClass("selected");
            $(item).find("img").prop("src", "/images/common/" + (index + 1) + ".jpg");
            //为当前li添加selected Class
        });
        $(this).addClass("selected");
        //修改当前li下的image的src
        $(this).find("img").prop("src", "/images/common/" + ($(this).index() + 1) + "_hover.jpg");
        //修改模块图片
        $("#nav_module img").prop("src", "/images/common/module_" + ($(this).index() + 1) + ".png");
        //切换z-tree
        treeInit($(this).data('rootmenu'));
    });

    //默认显示第一个
    treeInit("business");
});

//z-tree配置
var setting = {
    data: {
        simpleData: {
            enable: true
        },
    },
    callback: {
        onClick: function (event, treeId, treeNode) {
            if (treeNode.action) {
                $("#rightMain").attr("src", treeNode.action);
            };
            //系统面包屑
            if (treeNode.getParentNode()) {
                $("#here_area").html("当前位置：系统&nbsp;>&nbsp;" + treeNode.getParentNode().name + "&nbsp;>&nbsp;" + treeNode.name);
            } else {
                $("#here_area").html("当前位置：系统&nbsp;>&nbsp;" + treeNode.name);
            }

        }
    },
    async: {
        enable: true,
        url: "/systemmenu/loadMenus.do",
        autoParam: ["sn=ParentSn"]
    }
};
var zNodes = {
    "business": {id: 1, pId: 0, name: "业务模块", isParent: true, sn: "business"},
    "systemManage": {id: 2, pId: 0, name: "系统模块", isParent: true, sn: "system"},
    "charts": {id: 3, pId: 0, name: "报表模块", isParent: true, sn: "charts"}
};

function treeInit(data) {
    $.fn.zTree.init($("#dleft_tab1"), setting, zNodes[data]);
}