package com.hyl.wms.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class OrderBillQueryObject extends BaseBillQueryObject {

    //供应商
    private Long supplierId = -1L;

}
