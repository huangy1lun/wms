package com.hyl.wms.mapper;

import com.hyl.wms.domain.ProductStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductStockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductStock record);

    ProductStock selectByPrimaryKey(Long id);

    List<ProductStock> selectAll();

    int updateByPrimaryKey(ProductStock record);

    ProductStock queryByProductIdAndBillId(@Param("productId") Long productId, @Param("depotId") Long depotId);
}