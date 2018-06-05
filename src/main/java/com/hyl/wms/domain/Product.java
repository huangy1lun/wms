package com.hyl.wms.domain;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Product extends BaseDomain {

    private String name;

    private String sn;

    private BigDecimal costPrice;

    private BigDecimal salePrice;

    private String imagePath;

    private String intro;

    private Long brandId;

    private String brandName;

    public String getSmallImagePath() {
        if (StringUtils.isEmpty(imagePath)) {
            return "";
        }
        String prefix = imagePath.substring(0, imagePath.lastIndexOf("."));
        String suffix = imagePath.substring(imagePath.lastIndexOf("."));
        return prefix + "_small" + suffix;
    }

    public String getJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("id",getId());
        map.put("name",name);
        map.put("brandName", brandName);
        map.put("costPrice", costPrice);
        map.put("salePrice", salePrice);
        return JSON.toJSONString(map);
    }

}