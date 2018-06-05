package com.hyl.wms.service.impl;

import com.hyl.wms.domain.Depot;
import com.hyl.wms.mapper.DepotMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IDepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepotServiceImpl implements IDepotService {

    @Autowired
    private DepotMapper depotMapper;


    @Override
    public List<Depot> list() {
        return depotMapper.selectAll();
    }

    @Override
    public void saveOrUpdate(Depot depot) {
        if(depot.getId() != null){
            depotMapper.updateByPrimaryKey(depot);
        } else {
            depotMapper.insert(depot);
        }
    }

    @Override
    public Depot get(Long id) {
        return depotMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        depotMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count  = depotMapper.queryForCount(qo);
        if(count == null){
            return PageResult.empty(qo.getPageSize());
        }
        List<Depot> list = depotMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,list);
    }
}
