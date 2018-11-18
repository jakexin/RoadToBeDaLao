package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.po.Customer;
import com.shsxt.crm.po.CustomerOrder;
import com.shsxt.crm.query.CustomerOrderQuery;
import com.shsxt.crm.service.CustomerOrderService;
import com.shsxt.crm.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by xlf on 2018/7/27.
 */
@Controller
@RequestMapping("customerOrder")
public class CustomerOrderController extends BaseController {

    @Resource
    private CustomerService customerService;

    @Resource
    private CustomerOrderService customerOrderService;

    @RequestMapping("index")
    public String index(Integer cid, Model model){
        Customer customer = customerService.queryById(cid);
        model.addAttribute(customer);
        return "customer_order";
    }

    @RequestMapping("queryCustomerOrderByParams")
    @ResponseBody
    public Map<String, Object> queryCustomerOrderByParams(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows,
            CustomerOrderQuery query){
        query.setPageNum(page);
        query.setPageSize(rows);
        return customerOrderService.queryForPage(query);
    }

    @RequestMapping("queryCustomerOrderById")
    @ResponseBody
    public CustomerOrder queryCustomerOrderById(Integer id){
        return customerOrderService.queryById(id);
    }


}
