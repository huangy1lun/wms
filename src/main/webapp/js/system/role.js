$(function () {
    //全部右移
    $("#selectAll").click(function () {
        $("#all_permission option").appendTo($("#self_permission"));
    });
    //全部左移
    $("#deselectAll").click(function () {
        $("#self_permission option").appendTo($("#all_permission"));
    });
    //选中右移
    $("#select").click(function () {
        $("#all_permission option:selected").appendTo($("#self_permission"));
    });
    //选中左移
    $("#deselect").click(function () {
        $("#self_permission option:selected").appendTo($("#all_permission"));
    });

    //权限回显
    var arr = $("#self_permission option").map(function(index,item){
        return $(item).val();
    });
    $("#all_permission option").each(function (index, item) {
        if ($.inArray($(item).val(),arr) > -1) {
            $(item).remove();
        }
    })
})

$(function () {
    //全部右移
    $("#mSelectAll").click(function () {
        $("#all_menus option").appendTo($("#self_menus"));
    });
    //全部左移
    $("#mDeselectAll").click(function () {
        $("#self_menus option").appendTo($("#all_menus"));
    });
    //选中右移
    $("#mSelect").click(function () {
        $("#all_menus option:selected").appendTo($("#self_menus"));
    });
    //选中左移
    $("#mDeselect").click(function () {
        $("#self_menus option:selected").appendTo($("#all_menus"));
    });

    //权限回显
    var arr = $("#self_menus option").map(function(index,item){
        return $(item).val();
    });
    $("#all_menus option").each(function (index, item) {
        if ($.inArray($(item).val(),arr) > -1) {
            $(item).remove();
        }
    })
})