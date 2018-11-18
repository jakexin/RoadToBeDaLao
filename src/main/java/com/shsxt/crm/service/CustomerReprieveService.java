package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CustomerReprieveMapper;
import com.shsxt.crm.po.CustomerReprieve;
import com.shsxt.crm.utils.AssertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xlf on 2018/7/28.
 */
@Service
public class CustomerReprieveService extends BaseService<CustomerReprieve>{

    @Resource
    private CustomerReprieveMapper customerReprieveMapper;


    public void saveOrUpdate(CustomerReprieve customerReprieve) {
        /***
         * 1. 校验参数
         * 2. 判断添加或者更新
         * 3. 补全参数
         * 4. 执行操作
         * */
        if(null == customerReprieve.getId()){
            // 添加
            customerReprieve.setCreateDate(new Date());
            customerReprieve.setUpdateDate(new Date());
            AssertUtil.isTrue(customerReprieveMapper.save(customerReprieve)<1, CrmConstant.OPS_FAILED_MSG);
        }else{
            customerReprieve.setUpdateDate(new Date());
            AssertUtil.isTrue(customerReprieveMapper.update(customerReprieve)<1, CrmConstant.OPS_FAILED_MSG);
        }
    }

    public List<Map> queryCustomerServePer(){
        return customerReprieveMapper.queryCustomerServePer();
    }

    public List<Integer> queryCustomerGouCheng(){
        return customerReprieveMapper.queryCustomerGouCheng();
    }
}
