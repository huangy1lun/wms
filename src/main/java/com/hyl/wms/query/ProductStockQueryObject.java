package com.hyl.wms.query;


import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProductStockQueryObject extends QueryObject {

    private String keyword;

    private Long depotId = -1L;

    private Long brandId = -1L;

    private Long limitNumber;

    public void setKeyword(String keyword) {
        if (keyword == null || "".equals(keyword.trim())) {
            this.keyword = null;
        } else {
            this.keyword = keyword;
        }
    }
}
