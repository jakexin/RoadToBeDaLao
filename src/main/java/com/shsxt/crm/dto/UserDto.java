package com.shsxt.crm.dto;

import com.shsxt.crm.po.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author xlf
 * @date 2018/7/24
 */
public class UserDto extends User {
    private String roleName;

    //private Integer[] roleIds = null;
    private List<Integer> roleIds=new ArrayList<Integer>();

    private String roleIdsStr;

    public String getRoleIdsStr() {
        return roleIdsStr;
    }

    public void setRoleIdsStr(String roleIdsStr) {
        this.roleIdsStr = roleIdsStr;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
