package com.hyl.wms.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class StockIncomeBillQueryObject extends QueryObject {

    //查询开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    //查询结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    //供应商
    private Long depotId = -1L;
    //审核状态
    private Long status = -1L;

    public void setEndDate(Date endDate) {
        if (endDate == null) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        this.endDate = calendar.getTime();
    }
}
