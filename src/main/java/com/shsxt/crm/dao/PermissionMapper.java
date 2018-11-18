package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseDao;
import com.shsxt.crm.po.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface PermissionMapper extends BaseDao<Permission>{
    public Integer queryPermissionsByRoleId(Integer roleId);
    public Integer deleteAllModulePermissionByRoleId(Integer roleId);
    public List<String> queryAllModuleAclValueByUserId(Integer userId);
    public Integer deletePermissionByAclValue(String aclValue);
}