package com.hyl.wms.service;

import com.hyl.wms.domain.Employee;
import com.hyl.wms.page.PageResult;
import com.hyl.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IEmployeeService {

  List<Employee> list();

  void saveOrUpdate(Employee employee, Long[] ids);

  Employee get(Long id);

  void delete(Long id);

  PageResult query(QueryObject qo);

  void checkLogin(Employee employee);

  void batchDelete(Long[] ids);
}

