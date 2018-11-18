package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseDao;
import com.shsxt.crm.po.CustomerReprieve;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomerReprieveMapper extends BaseDao<CustomerReprieve>{
    public List<Map> queryCustomerServePer();
    public List<Integer> queryCustomerGouCheng();
}