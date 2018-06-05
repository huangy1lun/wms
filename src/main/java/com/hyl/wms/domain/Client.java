package com.hyl.wms.domain;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Client extends BaseDomain{

    private String name;

    private String sn;

    private String phone;

}