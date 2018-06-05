package com.hyl.wms.service;

import com.hyl.wms.domain.Depot;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;

import java.util.List;

public interface IDepotService {

  List<Depot> list();

  void saveOrUpdate(Depot depot);

  Depot get(Long id);

  void delete(Long id);

  PageResult query(QueryObject qo);
}
