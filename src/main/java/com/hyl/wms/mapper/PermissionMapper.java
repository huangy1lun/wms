package com.hyl.wms.mapper;

import com.hyl.wms.domain.Permission;
import com.hyl.wms.domain.Permission;
import com.hyl.wms.query.QueryObject;

import java.util.List;
import java.util.Set;

public interface PermissionMapper {
    
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    List<Permission> queryForList(QueryObject qo);

    Integer queryForCount(QueryObject qo);

    Set<String> queryPermissionByEmployeeId(Long id);
}