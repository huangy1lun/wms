package com.hyl.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Employee extends BaseDomain {

    private String name;

    private String password;

    private String email;

    private Integer age;

    private boolean admin;

    private Department dept;

    private List<Role> roleList;

    public Integer getAge() {
        if(age == null){
            return 0;
        } else {
            return age;
        }
    }

    public String getRoleNames() {
        //判断是否是超级管理员
        if (this.admin) {
            return "超级管理员";
        }
        //判断是否有角色
        if(this.getRoleList().size() == 0){
            return "无";
        }
        StringBuilder sb = new StringBuilder();
        for (Role role : this.getRoleList()) {
            sb.append(role.getName()).append("，");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}