package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseDao;
import com.shsxt.crm.po.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper extends BaseDao<UserRole>{
    public Integer deleteRoleByUserId(Integer userId);
    public Integer deleteRoleByRoleId(Integer roleId);
    public Integer queryRolesByUserId(Integer userId);
    public Integer queryRolesByRoleId(Integer roleId);
}