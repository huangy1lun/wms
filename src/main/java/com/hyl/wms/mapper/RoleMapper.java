package com.hyl.wms.mapper;

import com.hyl.wms.domain.Role;
import com.hyl.wms.domain.Role;
import com.hyl.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    List<Role> queryForList(QueryObject qo);

    Integer queryForCount(QueryObject qo);

    void deleteRelationRoleAndPermission(Long id);

    void insertRelationRoleAndPermission(@Param("rId") Long id, @Param("pId") Long pId);

    void deleteRelationRoleAndMenu(Long id);

    void insertRelationRoleAndMenu(@Param("rId") Long rId, @Param("menuId") Long menuId);
}