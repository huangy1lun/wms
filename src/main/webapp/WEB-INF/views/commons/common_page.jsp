
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    $(function () {
        $(".btn_page").click(function () {
            $("input[name=currentPage]").val($(this).data("page"));
            $("#searchForm").submit();
        })
        $(".btn_select").change(function () {
            $("input[name=currentPage]").val(1);
            $("#searchForm").submit();
        })
        $("select[name=pageSize] option").each(function (index, item) {
            if($(item).val() == "${result.pageSize}"){
                $(item).prop("selected", true);
            }
        })
    })
</script>
<div class="ui_tb_h30">
    <div class="ui_flt" style="height: 30px; line-height: 30px;">
        共有
        <span class="ui_txt_bold04">${result.totalCount}</span>
        条记录，当前第
        <span class="ui_txt_bold04">${result.currentPage}/${result.totalPage}</span>
        页
    </div>
    <div class="ui_frt">
        <input type="button" value="首页" class="ui_input_btn01 btn_page" data-page="1"/>
        <input type="button" value="上一页" class="ui_input_btn01 btn_page" data-page="${result.prevPage}"/>
        <input type="button" value="下一页" class="ui_input_btn01 btn_page" data-page="${result.nextPage}"/>
        <input type="button" value="尾页" class="ui_input_btn01 btn_page" data-page="${result.totalPage}"/>

        <select list="{5,10,20,50}" name="pageSize" class="ui_select02 btn_select">
            <option>5</option>
            <option>10</option>
            <option>20</option>
            <option>50</option>
        </select>
        转到第<input type="text" name="currentPage" value="${result.currentPage}" class="ui_input_txt01"/>页
        <input type="submit" class="ui_input_btn01" value="跳转"/>
    </div>
</div>
