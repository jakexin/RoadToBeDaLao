package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.SaleChanceMapper;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.po.User;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by xlf on 2018/7/20.
 */
@Service
public class SaleChanceService extends BaseService<SaleChance> {

    @Resource
    private SaleChanceMapper saleChanceMapper;

    @Resource
    private UserDao userDao;

    public void saveOrUpdateSaleChance(SaleChance saleChance, Integer userId){
        /***
         * 添加或者更新SaleChance
         * 1. 如何区分时添加或者时更新: 通过id
         *      如果有id代表就是更新操作,否则是添加操作
         * 2. 数据有些是前台传入, 一部分后台补充
         *      前台传入: 客户名称 联系方式 ...
         *      后台补充: 创建时间 创建人 更新时间 ...
         * */
        Integer id = saleChance.getId();
        saleChance.setUpdateDate(new Date());// 更新时间
        User user = userDao.queryById(userId);// 获取当前登陆用户
        // 参数校验(省略...)

        if(null == id){
            // 添加
            // 补充参数

            saleChance.setCreateMan(user.getUserName());//创建人
            /***
             * 如果有分配人: 就是已分配 state 1  开发中 1 分配人 和 分配时间
             * 如果没有分配人: 就是 未分配 state 0 未开发 0
             * */
            if(StringUtils.isBlank(saleChance.getAssignMan())){
                saleChance.setState(0);//未分配
                saleChance.setDevResult(0);// 未开发
            }else{
                saleChance.setState(1);//已分配
                saleChance.setDevResult(1);// 已开发
                saleChance.setAssignTime(new Date());// 分配时间
            }

            saleChance.setCreateDate(new Date());// 创建时间
            AssertUtil.isTrue(saleChanceMapper.save(saleChance)<1, "添加失败");
        }else{
            if(!StringUtils.isBlank(saleChance.getAssignMan())){
                saleChance.setState(1);//已分配
                saleChance.setDevResult(1);// 已开发
                saleChance.setAssignTime(new Date());// 分配时间
            }
            // 更新
//            saleChance.setUpdateDate(new Date());// 更新时间
            AssertUtil.isTrue(saleChanceMapper.update(saleChance)<1, "更新失败");
        }
    }

    public void deleteSaleChances(Integer[] ids){
        saleChanceMapper.deleteBatch(ids);
    }
}
