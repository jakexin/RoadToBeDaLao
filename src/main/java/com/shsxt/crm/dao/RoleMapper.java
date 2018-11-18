package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseDao;
import com.shsxt.crm.dto.PermissionDto;
import com.shsxt.crm.po.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Repository
public interface RoleMapper extends BaseDao<Role>{

    /**
     * @return
     */
    List<Map> queryAllRoles();

    /**
     * @param roleId
     * @return
     */
    List<PermissionDto> queryModulePermissionByRoleId(Integer roleId);
}