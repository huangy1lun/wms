package com.hyl.wms.domain;


import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SystemMenu extends BaseDomain{

    private String name;

    private String url;

    private String sn;

    private SystemMenu parent;

}