package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.CusDevPlan;
import com.shsxt.crm.query.CusDevPlanQuery;
import com.shsxt.crm.service.CusDevPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 * @author xlf
 * @date 2018/7/23
 */
@Controller
@RequestMapping("cusDevPlan")
public class CusDevPlanController extends BaseController {

    @Resource
    private CusDevPlanService cusDevPlanService;

    @RequestMapping("queryCusDevPlans")
    @ResponseBody
    public Map<String, Object> queryCusDevPlans(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows,
            CusDevPlanQuery query) {
        query.setPageNum(page);
        query.setPageSize(rows);
        return cusDevPlanService.queryForPage(query);
    }

    @RequestMapping("saveOrUpdateCusDevPlan")
    @ResponseBody
    public ResultInfo saveOrUpdateCusDevPlan(CusDevPlan cusDevPlan, Integer sid) {
        cusDevPlanService.saveOrUpdate(cusDevPlan, sid);
        return success("操作成功");
    }

    @RequestMapping("deleteBatchCusDevPlan")
    @ResponseBody
    public ResultInfo deleteBatchCusDevPlan(Integer[] ids){
        cusDevPlanService.deleteBatchCusDevPlan(ids);
        return success("操作成功");
    }
}
