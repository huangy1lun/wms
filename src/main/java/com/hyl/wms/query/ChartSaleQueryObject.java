package com.hyl.wms.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


@Getter@Setter
public class ChartSaleQueryObject {

    //查询开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    //查询结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private Long clientId = -1L;

    private Long brandId = -1L;

    private String groupByType = "e.name";

    public static final Map<String ,String> groupTypes = new LinkedHashMap<>();

    static {
        groupTypes.put("e.name","销售人员");
        groupTypes.put("p.name","货品名称");
        groupTypes.put("c.name","客户");
        groupTypes.put("p.brand_name","品牌");
        groupTypes.put("DATE_FORMAT(sa.vdate,\'%Y-%m\')","订货日期(月)");
        groupTypes.put("DATE_FORMAT(sa.vdate,\'%Y-%m-%d\')","订货日期(日)");
    }

}
