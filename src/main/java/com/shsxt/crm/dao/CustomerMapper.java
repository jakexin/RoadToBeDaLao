package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseDao;
import com.shsxt.crm.po.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Repository
public interface CustomerMapper extends BaseDao<Customer> {
    /**
     * 没啥好说的
     * @param dicName
     * @return
     */
    List<Map> queryCustomerLevel(String dicName);

    /**
     * 没啥
     * @return
     */
    List<Customer> queryLossCustomer();

    /**
     * 更新用户状态
     * @param ids
     * @return
     */
    Integer updateCustomerState(Integer[] ids);

}