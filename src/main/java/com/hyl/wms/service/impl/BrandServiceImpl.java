package com.hyl.wms.service.impl;

import com.hyl.wms.domain.Brand;
import com.hyl.wms.mapper.BrandMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;


    @Override
    public List<Brand> list() {
        return brandMapper.selectAll();
    }

    @Override
    public void saveOrUpdate(Brand brand) {
        if(brand.getId() != null){
            brandMapper.updateByPrimaryKey(brand);
        } else {
            brandMapper.insert(brand);
        }
    }

    @Override
    public Brand get(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count  = brandMapper.queryForCount(qo);
        if(count == null){
            return PageResult.empty(qo.getPageSize());
        }
        List<Brand> list = brandMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,list);
    }
}
