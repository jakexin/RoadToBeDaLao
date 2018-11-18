package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CustomerMapper;
import com.shsxt.crm.po.Customer;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.MathUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author xlf
 * @date 2018/7/27
 */
@Service
public class CustomerService extends BaseService<Customer>{

    @Resource
    private CustomerMapper customerMapper;

    public List<Map> queryCustomerLevel(String dicName){
        return customerMapper.queryCustomerLevel(dicName);
    }

    public void saveOrUpdate(Customer customer){
        /***
         *
         * */
        // 检查参数: 必填...

        if(null==customer.getId()){
            // 添加
            // 客户编号 state 0(未流失) 1(已流失) isValid 创建时间和更新时间
            customer.setKhno(MathUtil.genereateKhCode());
            customer.setState(0);//未流失
            customer.setIsValid(1);//有效
            customer.setCreateDate(new Date());
            customer.setUpdateDate(new Date());
            AssertUtil.isTrue(customerMapper.save(customer)<1, CrmConstant.OPS_FAILED_MSG);
        }else{
            customer.setUpdateDate(new Date());
            AssertUtil.isTrue(customerMapper.update(customer)<1, CrmConstant.OPS_FAILED_MSG);
        }
    }

    public List<Customer> queryLossCustomer(){
        return customerMapper.queryLossCustomer();
    }
}
