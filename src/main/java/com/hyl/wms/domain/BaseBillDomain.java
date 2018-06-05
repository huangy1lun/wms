package com.hyl.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter@Setter
public class BaseBillDomain extends BaseDomain{

    //未审核状态
    public static final int BILL_STATUS_NORMAL = 0;
    //已审核状态
    public static final int BILL_STATUS_AUDITED = 1;
    //审核状态
    private Integer status = BILL_STATUS_NORMAL;
    //审核时间
    private Date auditTime;
    //录入时间
    private Date inputTime;
    //录入人
    private Employee inputUser;
    //审核人
    private Employee auditor;




}
