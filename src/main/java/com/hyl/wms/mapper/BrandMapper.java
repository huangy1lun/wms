package com.hyl.wms.mapper;

import com.hyl.wms.domain.Brand;
import com.hyl.wms.query.QueryObject;

import java.util.List;

public interface BrandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Brand record);

    Brand selectByPrimaryKey(Long id);

    List<Brand> selectAll();

    int updateByPrimaryKey(Brand record);

    List<Brand> queryForList(QueryObject qo);

    Integer queryForCount(QueryObject qo);
}