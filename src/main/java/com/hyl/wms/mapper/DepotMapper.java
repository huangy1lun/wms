package com.hyl.wms.mapper;

import com.hyl.wms.domain.Depot;
import com.hyl.wms.query.QueryObject;

import java.util.List;

public interface DepotMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Depot record);

    Depot selectByPrimaryKey(Long id);

    List<Depot> selectAll();

    int updateByPrimaryKey(Depot record);

    List<Depot> queryForList(QueryObject qo);

    Integer queryForCount(QueryObject qo);
}