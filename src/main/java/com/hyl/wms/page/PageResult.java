package com.hyl.wms.page;


import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class PageResult {

    private Integer currentPage;
    private Integer pageSize;

    private Integer prevPage;
    private Integer nextPage;
    private Integer totalPage;

    private Integer totalCount;
    private List<?> list;

    public static PageResult empty(Integer pageSize) {
        return new PageResult(1,pageSize,0, Collections.EMPTY_LIST);
    }

    public PageResult(Integer currentPage, Integer pageSize, Integer totalCount, List<?> list) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.list = list;
        if(this.totalCount > this.pageSize){
            this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        } else {
            this.totalPage = 1;
        }
        this.prevPage = currentPage - 1 < 1 ? 1 : currentPage - 1;
        this.nextPage = currentPage + 1 >= totalPage ? totalPage : currentPage + 1;
    }
}
