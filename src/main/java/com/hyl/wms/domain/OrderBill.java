package com.hyl.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

@Getter@Setter
public class OrderBill extends BaseBillDomain{

    //订单编号
    private String sn;
    //业务时间
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date vdate;
    //订单总金额
    private BigDecimal totalAmount;
    //订单总数量
    private BigDecimal totalNumber;
    //订单关联的供应商
    private Supplier supplier;
    //订单明细
    private List<OrderBillItem> items;

    public String getFormatVdate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(vdate);
    }

}