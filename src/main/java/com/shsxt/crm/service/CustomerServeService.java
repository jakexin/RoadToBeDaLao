package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CustomerServeMapper;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.po.CustomerServe;
import com.shsxt.crm.po.User;
import com.shsxt.crm.utils.AssertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by xlf on 2018/7/30.
 */
@Service
public class CustomerServeService extends BaseService<CustomerServe>{

    @Resource
    private CustomerServeMapper customerServeMapper;

    @Resource
    private UserDao userDao;

    public void saveOrUpdate(CustomerServe customerServe, Integer userId){
        /***
         * 1. 检查参数 (省略...)
         * 2. 判断添加或者修改
         * 3. 补全参数
         * 4. 执行操作
         * */
        /*  1服务创建 2服务分配 3服务处理 4 服务反馈 5服务归档 */

        //TODO 参数检查...

        customerServe.setUpdateDate(new Date());

        // 添加
        User user = userDao.queryById(userId);//查询出当前登陆用户
        if(null==customerServe.getId()){
            customerServe.setCreatePeople(user.getUserName());//设置创建人
            customerServe.setIsValid(1);//有效
            customerServe.setState("1");//1 创建

            customerServe.setCreateDate(new Date());
            //customerServe.setUpdateDate(new Date());

            AssertUtil.isTrue(customerServeMapper.save(customerServe)<1, CrmConstant.OPS_FAILED_MSG);
        }else{
            // 更新
            String state = customerServe.getState();
            if(state.equals("1")){
                customerServe.setState("2");
                customerServe.setAssignTime(new Date());
                //customerServe.setUpdateDate(new Date());
            }else if(state.equals("2")){
                customerServe.setState("3");
                customerServe.setServiceProcePeople(user.getUserName());
                customerServe.setServiceProceTime(new Date());
            }else if(state.equals("3")){
                customerServe.setState("5");
            }

            AssertUtil.isTrue(customerServeMapper.update(customerServe)<1, CrmConstant.OPS_FAILED_MSG);
        }

    }
}
