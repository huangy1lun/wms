package com.hyl.wms.mapper;

import com.hyl.wms.domain.Supplier;
import com.hyl.wms.query.QueryObject;

import java.util.List;

public interface SupplierMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Supplier record);

    Supplier selectByPrimaryKey(Long id);

    List<Supplier> selectAll();

    int updateByPrimaryKey(Supplier record);

    List<Supplier> queryForList(QueryObject qo);

    Integer queryForCount(QueryObject qo);
}