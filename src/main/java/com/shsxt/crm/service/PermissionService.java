package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.PermissionMapper;
import com.shsxt.crm.po.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author xlf
 * @date 2018/7/26
 */
@Service
public class PermissionService extends BaseService<Permission> {

    @Resource
    private PermissionMapper permissionMapper;

    public List<String> queryAllModuleAclValueByUserId(Integer userId){
        return  permissionMapper.queryAllModuleAclValueByUserId(userId);
    }
}
