package com.hyl.wms.mapper;

import com.hyl.wms.domain.ProductStock;
import com.hyl.wms.domain.StockIncomeBill;
import com.hyl.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductStockMapper {

    int insert(ProductStock record);

    int updateByPrimaryKey(ProductStock record);

    Integer queryForCount(QueryObject qo);

    List<ProductStock> queryForList(QueryObject qo);

    ProductStock queryByProductIdAndDepotId(@Param("productId") Long productId, @Param("depotId") Long depotId);
}