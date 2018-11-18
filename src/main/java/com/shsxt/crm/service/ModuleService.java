package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.ModuleMapper;
import com.shsxt.crm.dao.PermissionMapper;
import com.shsxt.crm.po.Module;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author xlf
 * @date 2018/7/26
 */
@Service
public class ModuleService extends BaseService<Module>{

    @Resource
    private ModuleMapper moduleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    /**
     *利用权限等级（0 1 2）查询父模块
     * @param grade
     * @return
     */
    public List<Map> queryParentModuleByGrade(Integer grade){
        return moduleMapper.queryParentModuleByGrade(grade);
    }

    /**
     * 添加更新
     * @param module
     */
    public void saveOrUpdate(Module module){
        /***
         * 1. 检查参数
         * 2. 判断添加还是修改
         * 3. 补全参数
         * 4. 数据库操作
         * */
        checkModuleParams(module);

        /***
         * 层级判断
         * 获取层级
         * 父级为null
         * */
        Integer grade = module.getGrade();
        if(0==grade){
            module.setParentId(null);
        }else if(1 ==grade || 2 ==grade){
            AssertUtil.isTrue(null==module.getParentId(),"缺少父级模块");
            // 通过parentId获取模块
            Module parentModule = moduleMapper.queryById(module.getParentId());
            AssertUtil.isTrue(null == parentModule, "父模块不存在");
            //获取grade
            AssertUtil.isTrue(grade-parentModule.getGrade()!=1, "层级关系不正确");
            //判断权限码格式是否正确
            AssertUtil.isTrue(module.getOptValue().indexOf(parentModule.getOptValue())!=0,"权限码格式不正确");
        }
        if(grade>=0 && grade<=2){
            // 做操作
            if(null==module.getId()){
                // 添加
                module.setIsValid((byte) 1);
                module.setCreateDate(new Date());
                module.setUpdateDate(new Date());
                AssertUtil.isTrue(moduleMapper.save(module)<1, CrmConstant.OPS_FAILED_MSG);
            }else{
                // 更新
                module.setUpdateDate(new Date());
                AssertUtil.isTrue(moduleMapper.update(module)<1, CrmConstant.OPS_FAILED_MSG);
            }
        }
    }
    /**
     * 检查模块参数
     * @param module
     */
    private void checkModuleParams(Module module) {
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()), "模块名为空");
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()), "权限码为空");
        AssertUtil.isTrue(null==module.getGrade(), "层级为空");

        /***
         * 权限码和模块名唯一校验
         * 规则:
         *  1. 添加
         *      权限码和模块名唯一
         *  2. 修改
         *      不允许修改权限码和模块名
         *      只能修改层级关系
         * */

        if(null==module.getId()){
            // 添加
            Module module1 = moduleMapper.queryByModuleName(module.getModuleName());
            Module module2 = moduleMapper.queryByOptValue(module.getOptValue());
            AssertUtil.isTrue(null!=module1, "模块名称重复");
            AssertUtil.isTrue(null!=module2, "权限码重复");
        }else{
            // 更新
            Module module3 = moduleMapper.queryById(module.getId());
            AssertUtil.isTrue(!module.getModuleName().equals(module3.getModuleName()),"模块名称不允许修改");
            AssertUtil.isTrue(!module.getOptValue().equals(module3.getOptValue()),"权限码不允许修改");
        }

    }

    /**
     * 通过删除 模块权限码，删除模块
     * @param optValue
     */
    public void deleteModulesByOptValue(String optValue){
        AssertUtil.isTrue(StringUtils.isBlank(optValue), "权限码为空");
        AssertUtil.isTrue(null == moduleMapper.queryByOptValue(optValue), "模块不存在");
        moduleMapper.deleteModulesByOptValue(optValue);
        // 级联删除 中间表 t_permission
        permissionMapper.deletePermissionByAclValue(optValue);
    }
}
