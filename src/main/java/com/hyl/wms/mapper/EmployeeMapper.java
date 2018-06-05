package com.hyl.wms.mapper;

import com.hyl.wms.domain.Employee;
import com.hyl.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface EmployeeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);

    Integer queryForCount(QueryObject qo);

    List<Employee> queryForList(QueryObject qo);

    void insertRelationEmployeeAndRole(@Param("eId") Long id, @Param("rId") Long rId);

    void deleteRelationEmployeeAndRole(Long id);

    Employee checkLoginByUsername(String name);

    Employee checkLoginByUser(@Param("name") String name, @Param("password") String password);

    Set<String> queryPermissionByEmployeeId(Long id);

    void batchDelete(@Param("ids") Long[] ids);

    void batchDeleteRelationEmployeeAndRole(@Param("ids") Long[] ids);
}
