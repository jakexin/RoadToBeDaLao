package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.CusDevPlanMapper;
import com.shsxt.crm.po.CusDevPlan;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 * @author xlf
 * @date 2018/7/23
 */
@Service
public class CusDevPlanService extends BaseService<CusDevPlan> {
    @Autowired
    private CusDevPlanMapper cusDevPlanMapper;

    /**
     * 更新和添加
     * @param cusDevPlan
     * @param sid
     */
    public void saveOrUpdate(CusDevPlan cusDevPlan, Integer sid){
        /***
         * 1. 参数检测
         * 2. 判断更新和添加
         * 3. 执行操作
         * */
        checkCusDevPlanParams(cusDevPlan);

        Integer id = cusDevPlan.getId();
        if (null == id){
            // 添加
            // 补充参数
            cusDevPlan.setCreateDate(new Date());
            cusDevPlan.setUpdateDate(new Date());
            cusDevPlan.setIsValid(1);
            cusDevPlan.setSaleChanceId(sid);
            AssertUtil.isTrue(cusDevPlanMapper.save(cusDevPlan)<1, "添加失败");
        }else{
            cusDevPlan.setUpdateDate(new Date());
            AssertUtil.isTrue(cusDevPlanMapper.update(cusDevPlan)<1, "更新失败");
        }
    }

    /**
     * 核查
     * @param cusDevPlan
     */
    private void checkCusDevPlanParams(CusDevPlan cusDevPlan) {
        AssertUtil.isTrue(null == cusDevPlan.getPlanDate(), "计划时间为空");
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()), "计划内容为空");
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getExeAffect()), "执行结果为空");
    }

    /**
     * 删除
     * @param ids
     */
    public void deleteBatchCusDevPlan(Integer[] ids){
        AssertUtil.isTrue(null==ids || ids.length==0, "删除记录不存");
        AssertUtil.isTrue(cusDevPlanMapper.deleteBatch(ids)<ids.length, "删除失败");
    }
}
