package com.hyl.wms.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeQueryObject extends QueryObject {

    private String keyword;

    private long deptId = -1;
}
