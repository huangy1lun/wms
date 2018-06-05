package com.hyl.wms.service.impl;

import com.hyl.wms.domain.Client;
import com.hyl.wms.mapper.ClientMapper;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import com.hyl.wms.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientMapper clientMapper;


    @Override
    public List<Client> list() {
        return clientMapper.selectAll();
    }

    @Override
    public void saveOrUpdate(Client client) {
        if(client.getId() != null){
            clientMapper.updateByPrimaryKey(client);
        } else {
            clientMapper.insert(client);
        }
    }

    @Override
    public Client get(Long id) {
        return clientMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        clientMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count  = clientMapper.queryForCount(qo);
        if(count == null){
            return PageResult.empty(qo.getPageSize());
        }
        List<Client> list = clientMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,list);
    }
}
