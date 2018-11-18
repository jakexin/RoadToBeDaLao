package com.shsxt.crm.controller;

import com.shsxt.crm.annotations.RequestPermission;
import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.query.SaleChanceQuery;
import com.shsxt.crm.service.SaleChanceService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by xlf on 2018/7/20.
 */
@Controller
@RequestMapping("saleChance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @RequestMapping("index")
    public String index() {
        return "sale_chance";
    }

    @RequestMapping("cusDev")
    public String cusDev() {
        return "cus_dev_plan";
    }

    @RequestMapping("cusDevDetail")
    public String cusDevDetail(Integer sid, Model model) {
        //System.out.println(sid);
        SaleChance saleChance = saleChanceService.queryById(sid);
        // 存入model
        model.addAttribute(saleChance);
        return "cus_dev_plan_detail";
    }

    @RequestPermission(aclValue = "101001")
    @RequestMapping("querySaleChanceByParams")
    @ResponseBody
    public Object querySaleChanceByParams(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows,
            SaleChanceQuery query) {
        query.setPageNum(page);
        query.setPageSize(rows);
        return saleChanceService.queryForPage(query);
    }

    @RequestPermission(aclValue = "101002")
    @RequestMapping("saveOrUpdateSaleChance")
    @ResponseBody
    public ResultInfo saveOrUpdateSaleChance(SaleChance saleChance,
                                             HttpServletRequest request) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);// 获取用户id
        saleChanceService.saveOrUpdateSaleChance(saleChance, userId);
        return success("操作成功");
    }

    @RequestPermission(aclValue = "101003")
    @RequestMapping("deleteSaleChance")
    @ResponseBody
    public ResultInfo deleteSaleChances(Integer[] ids){
        saleChanceService.deleteSaleChances(ids);
        return success("操作成功");
    }
}
