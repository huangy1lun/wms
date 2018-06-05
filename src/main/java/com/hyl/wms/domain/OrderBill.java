package com.hyl.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter@Setter
public class OrderBill extends BaseDomain {
    //未审核状态
    public static final int ORDER_BILL_STATUS_NORMAL = 0;
    //已审核状态
    public static final int ORDER_BILL_STATUS_AUDITED = 1;
    //订单编号
    private String sn;
    //业务时间
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date vdate;
    //审核状态
    private Integer status = ORDER_BILL_STATUS_NORMAL;
    //订单总金额
    private BigDecimal totalAmount;
    //订单总数量
    private BigDecimal totalNumber;
    //审核时间
    private Date auditTime;
    //录入时间
    private Date inputTime;
    //录入人
    private Employee inputUser;
    //审核人
    private Employee auditor;
    //订单关联的供应商
    private Supplier supplier;
    //订单明细
    private List<OrderBillItem> items;

    public String getFormatVdate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(vdate);
    }
}