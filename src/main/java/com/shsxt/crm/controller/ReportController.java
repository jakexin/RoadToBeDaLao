package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.service.CustomerReprieveService;
import com.shsxt.crm.service.CustomerServeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by xlf on 2018/7/30.
 */
@Controller
@RequestMapping("report")
public class ReportController extends BaseController{

    @Resource
    private CustomerReprieveService customerReprieveService;

    @RequestMapping("index/{id}")
    public String index(@PathVariable Integer id){
        if(id==1){
            return "customer_goucheng_report";
        }
        if(id==2){
            return "customer_serve_report";
        }

        return "error";
    }

    @RequestMapping("queryCustomerServePer")
    @ResponseBody
    public List<Map> queryCustomerServePer(){
        return customerReprieveService.queryCustomerServePer();
    }

    @RequestMapping("queryCustomerGouCheng")
    @ResponseBody
    public List<Integer> queryCustomerGouCheng(){
        return customerReprieveService.queryCustomerGouCheng();
    }

}
