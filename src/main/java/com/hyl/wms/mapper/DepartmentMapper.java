package com.hyl.wms.mapper;

import com.hyl.wms.domain.Department;
import com.hyl.wms.query.QueryObject;
import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    Department selectByPrimaryKey(Long id);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);

    List<Department> queryForList(QueryObject qo);

    Integer queryForCount(QueryObject qo);
}