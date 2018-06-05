package com.hyl.wms.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryObject {

    private Integer currentPage = 1;
    private Integer pageSize = 10;

    public Integer getStartIndex() {
        return (currentPage - 1) * pageSize;
    }
}
