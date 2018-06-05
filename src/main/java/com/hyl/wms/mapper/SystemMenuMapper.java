package com.hyl.wms.mapper;

import com.hyl.wms.domain.SystemMenu;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.vo.SystemMenusVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemMenuMapper {
    
    int deleteByPrimaryKey(Long id);

    int insert(SystemMenu record);

    SystemMenu selectByPrimaryKey(Long id);

    List<SystemMenu> selectAll();

    int updateByPrimaryKey(SystemMenu record);

    List<SystemMenu> queryForList(QueryObject qo);

    Integer queryForCount(QueryObject qo);

    void deleteByParentId(Long id);

    List<SystemMenu> queryByParentId(Long id);

    List<SystemMenusVO> loadMenus(String parentSn);

    List<SystemMenu> getAllModule();

    List<SystemMenusVO> loadMenusByEmployeeId(@Param("parentSn") String parentSn, @Param("empId") Long empId);

    void deleteRoleAndMenuByMenuId(Long id);
}