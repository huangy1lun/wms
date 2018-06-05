package com.hyl.wms.service.impl;

import com.hyl.wms.domain.Brand;
import com.hyl.wms.domain.Product;
import com.hyl.wms.exception.LogicException;
import com.hyl.wms.mapper.ProductMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IBrandService;
import com.hyl.wms.service.IProductService;
import com.hyl.wms.service.IPermissionService;
import com.hyl.wms.util.MD5;
import com.hyl.wms.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private IBrandService brandService;

    @Autowired
    private IPermissionService permissionService;

    @Override
    public List<Product> list() {
        return productMapper.selectAll();
    }

    @Override
    public void saveOrUpdate(Product product) {
        //查询当前商品的品牌名称
        Brand brand = brandService.get(product.getBrandId());
        product.setBrandName(brand.getName());
        if(product.getId() != null){
            //编辑
            productMapper.updateByPrimaryKey(product);
        } else {
            //新建
            productMapper.insert(product);
        }
    }

    @Override
    public Product get(Long id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        //删除商品
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count  = productMapper.queryForCount(qo);
        if(count == null){
            return PageResult.empty(qo.getPageSize());
        }
        List<Product> list = productMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,list);
    }

    @Override
    public void batchDelete(Long[] ids) {
        //删除商品
        productMapper.batchDelete(ids);
    }
}
