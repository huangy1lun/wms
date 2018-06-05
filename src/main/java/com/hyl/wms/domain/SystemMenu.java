package com.hyl.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SystemMenu extends BaseDomain{

    private String name;

    private String url;

    private String sn;
    //父菜单
    private SystemMenu parent;


}