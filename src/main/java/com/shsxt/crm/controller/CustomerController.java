package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.Customer;
import com.shsxt.crm.query.CustomerQuery;
import com.shsxt.crm.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 * @author xlf
 * @date 2018/7/27
 */
@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController{
    @Resource
    private CustomerService customerService;

    @RequestMapping("index")
    public String index(){
        return "customer";
    }

    @RequestMapping("queryCustomersByParams")
    @ResponseBody
    public Map<String, Object> queryCustomerByParams(@RequestParam(defaultValue = "1") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer rows,
                                                     CustomerQuery query){
        query.setPageNum(page);
        query.setPageSize(rows);
        return customerService.queryForPage(query);
    }

    @RequestMapping("queryDataDicsByDicName")
    @ResponseBody
    public List<Map> queryCustomerLevel(String dicName){
        return customerService.queryCustomerLevel(dicName);
    }

    @RequestMapping("saveOrUpdateCustomer")
    @ResponseBody
    public ResultInfo saveOrUpdateCustomer(Customer customer){
        customerService.saveOrUpdate(customer);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("deleteCustomers")
    @ResponseBody
    public ResultInfo deleteCustomers(Integer[] ids){
        customerService.deleteBatch(ids);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("queryLossCustomer")
    @ResponseBody
    public List<Customer> queryLossCustomer(){
        return customerService.queryLossCustomer();
    }

}
