package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.OrderDetailsMapper;
import com.shsxt.crm.po.OrderDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by xlf on 2018/7/28.
 */
@Service
public class OrderDetailsService extends BaseService<OrderDetails>{

    @Resource
    private OrderDetailsMapper orderDetailsMapper;
}
