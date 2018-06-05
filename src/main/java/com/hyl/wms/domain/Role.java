package com.hyl.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Role extends BaseDomain{

    private String name;

    private String sn;

    private List<Permission> permissionList;

    private List<SystemMenu> menuList;

}