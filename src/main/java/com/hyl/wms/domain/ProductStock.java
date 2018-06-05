package com.hyl.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter@Setter
public class ProductStock extends BaseDomain{
    //成本价
    private BigDecimal price;
    //库存数量
    private BigDecimal storeNumber;
    //库存总价
    private BigDecimal amount;
    //商品
    private Product product;
    //仓库
    private Depot depot;

}