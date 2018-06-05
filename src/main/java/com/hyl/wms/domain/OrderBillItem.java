package com.hyl.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter@Setter
public class OrderBillItem extends BaseDomain {

    private BigDecimal costPrice;

    private BigDecimal number;

    private BigDecimal amount;

    private String remark;

    private Product product;

    private Long billId;

}