package com.hyl.wms.mapper;

import com.hyl.wms.domain.Brand;
import com.hyl.wms.domain.Client;
import com.hyl.wms.query.QueryObject;

import java.util.List;

public interface ClientMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Client record);

    Client selectByPrimaryKey(Long id);

    List<Client> selectAll();

    int updateByPrimaryKey(Client record);

    List<Client> queryForList(QueryObject qo);

    Integer queryForCount(QueryObject qo);
}