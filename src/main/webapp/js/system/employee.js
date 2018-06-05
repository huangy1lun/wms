$(function () {
    //全部右移
    $("#selectAll").click(function () {
        $("#all_roles option").appendTo($("#self_roles"));
    });
    //全部左移
    $("#deselectAll").click(function () {
        $("#self_roles option").appendTo($("#all_roles"));
    });
    //选中右移
    $("#select").click(function () {
        $("#all_roles option:selected").appendTo($("#self_roles"));
    });
    //选中左移
    $("#deselect").click(function () {
        $("#self_roles option:selected").appendTo($("#all_roles"));
    });

    //权限回显
    var arr = $("#self_roles option").map(function(index,item){
        return $(item).val();
    });
    $("#all_roles option").each(function (index, item) {
        if ($.inArray($(item).val(),arr) > -1) {
            $(item).remove();
        }
    })
})