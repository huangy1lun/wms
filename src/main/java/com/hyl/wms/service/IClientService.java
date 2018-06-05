package com.hyl.wms.service;

import com.hyl.wms.domain.Client;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;

import java.util.List;

public interface IClientService {

  List<Client> list();

  void saveOrUpdate(Client client);

  Client get(Long id);

  void delete(Long id);

  PageResult query(QueryObject qo);
}
