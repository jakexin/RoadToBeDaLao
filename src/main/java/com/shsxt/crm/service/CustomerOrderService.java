package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.CustomerOrderMapper;
import com.shsxt.crm.po.CustomerOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by xlf on 2018/7/28.
 */
@Service
public class CustomerOrderService extends BaseService<CustomerOrder> {

    @Resource
    private CustomerOrderMapper customerOrderMapper;
}
