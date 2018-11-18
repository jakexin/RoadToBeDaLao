package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.CustomerLoss;
import com.shsxt.crm.po.CustomerReprieve;
import com.shsxt.crm.query.CustomerReprieveQuery;
import com.shsxt.crm.service.CustomerLossService;
import com.shsxt.crm.service.CustomerReprieveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by xlf on 2018/7/28.
 */
@Controller
@RequestMapping("customerReprieve")
public class CustomerReprieveController extends BaseController{

    @Resource
    private CustomerLossService customerLossService;

    @Resource
    private CustomerReprieveService customerReprieveService;

    @RequestMapping("index")
    public String index(Integer lossId, Model model){
        CustomerLoss customerLoss = customerLossService.queryById(lossId);
        model.addAttribute(customerLoss);
        return "customer_loss_reprieve";
    }

    @RequestMapping("queryCustomerReprieveByParams")
    @ResponseBody
    public Map<String, Object> queryCustomerReprieveByParams(@RequestParam(defaultValue = "1") Integer page,
                                                             @RequestParam(defaultValue = "10") Integer rows,
                                                             CustomerReprieveQuery query){
        query.setPageNum(page);
        query.setPageSize(rows);
        return customerReprieveService.queryForPage(query);
    }

    @RequestMapping("saveOrUpdateCustomerReprieve")
    @ResponseBody
    public ResultInfo saveOrUpdateCustomerReprieve(CustomerReprieve customerReprieve){
        customerReprieveService.saveOrUpdate(customerReprieve);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("deleteCustomerReprieve")
    @ResponseBody
    public ResultInfo deleteCustomerReprieve(Integer[] ids){
        customerReprieveService.deleteBatch(ids);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }
}
