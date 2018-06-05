package com.hyl.wms.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter@Setter@ToString
public class StockIncomeBill extends BaseDomain{

    public static final int STATUS_NORMAL = 0;

    public static final int STATUS_AUDIT = 1;

    private String sn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vdate;

    private Integer status = STATUS_NORMAL;

    private BigDecimal totalAmount;

    private BigDecimal totalNumber;

    private Date auditTime;

    private Date inputTime;

    private Employee inputUser;

    private Employee auditUser;

    private Depot depot;

    private List<StockIncomeBillItem> items;

    public String getFormatVdate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(vdate);
    }
}