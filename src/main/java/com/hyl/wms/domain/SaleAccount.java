package com.hyl.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter@Setter
public class SaleAccount extends BaseDomain {

    private Date vdate;

    private BigDecimal number;

    private BigDecimal costPrice;

    private BigDecimal costAmount;

    private BigDecimal salePrice;

    private BigDecimal saleAmount;

    private Long productId;

    private Long salemanId;

    private Long clientId;

}