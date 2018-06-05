package com.hyl.wms.service.impl;

import com.hyl.wms.domain.Supplier;
import com.hyl.wms.mapper.SupplierMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements ISupplierService {

    @Autowired
    private SupplierMapper supplierMapper;


    @Override
    public List<Supplier> list() {
        return supplierMapper.selectAll();
    }

    @Override
    public void saveOrUpdate(Supplier supplier) {
        if(supplier.getId() != null){
            supplierMapper.updateByPrimaryKey(supplier);
        } else {
            supplierMapper.insert(supplier);
        }
    }

    @Override
    public Supplier get(Long id) {
        return supplierMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        supplierMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count  = supplierMapper.queryForCount(qo);
        if(count == null){
            return PageResult.empty(qo.getPageSize());
        }
        List<Supplier> list = supplierMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,list);
    }
}
