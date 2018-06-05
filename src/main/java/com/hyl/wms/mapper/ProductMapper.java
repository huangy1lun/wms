package com.hyl.wms.mapper;

import com.hyl.wms.domain.Department;
import com.hyl.wms.domain.Product;
import com.hyl.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    Product selectByPrimaryKey(Long id);

    List<Product> selectAll();

    int updateByPrimaryKey(Product record);

    List<Product> queryForList(QueryObject qo);

    Integer queryForCount(QueryObject qo);

    void batchDelete(@Param("ids") Long[] ids);
}