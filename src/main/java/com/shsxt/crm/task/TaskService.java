package com.shsxt.crm.task;

import com.shsxt.crm.service.CustomerLossService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by xlf on 2018/7/28.
 */
//@Component
public class TaskService {

    @Resource
    private CustomerLossService customerLossService;

    //@Scheduled(cron="0/2 * * * * ?")
    public void job01(){
        customerLossService.saveLossCustomerBatch();
    }
}
