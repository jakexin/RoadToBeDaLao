package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CustomerLossMapper;
import com.shsxt.crm.dao.CustomerMapper;
import com.shsxt.crm.po.Customer;
import com.shsxt.crm.po.CustomerLoss;
import com.shsxt.crm.utils.AssertUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author xlf
 * @date 2018/7/28
 */
@Service
public class CustomerLossService extends BaseService<CustomerLoss> {
    @Resource
    private CustomerLossMapper customerLossMapper;

    @Resource
    private CustomerMapper customerMapper;

    public void saveLossCustomerBatch() {
        // 查询出流失客户
        //创建一个集合，用于放查询结果
        List<Customer> lossCustomers = customerMapper.queryLossCustomer();

        if(!CollectionUtils.isEmpty(lossCustomers)){
            // 不为空
            List<CustomerLoss> lossList = new ArrayList<CustomerLoss>();
            List<Integer> customerIds = new ArrayList<Integer>();

            for(Customer customer: lossCustomers){
                CustomerLoss customerLoss = new CustomerLoss();
                customerLoss.setCusNo(customer.getKhno());
                customerLoss.setCusName(customer.getName());
                customerLoss.setCusManager(customer.getCusManager());
                customerLoss.setState(0);//暂未流失, 确认流失后变为1
                customerLoss.setIsValid(1);
                customerLoss.setCreateDate(new Date());
                customerLoss.setUpdateDate(new Date());


                lossList.add(customerLoss);
                customerIds.add(customer.getId());
            }
            // 保存流失客户到流失表
            AssertUtil.isTrue(customerLossMapper.saveBatch(lossList)<lossList.size(), CrmConstant.OPS_FAILED_MSG);
            // 更改客户表state状态
            Integer[] ids = new Integer[customerIds.size()];
            customerIds.toArray(ids);
            AssertUtil.isTrue(customerMapper.updateCustomerState(ids)<ids.length, CrmConstant.OPS_FAILED_MSG);
        }

    }
}
