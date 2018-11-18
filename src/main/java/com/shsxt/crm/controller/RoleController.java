package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.dto.PermissionDto;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.query.RoleQuery;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 * @author xlf
 * @date 2018/7/24
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    @RequestMapping("index")
    public String index() {
        return "role";
    }

    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map> queryAllRoles() {
        return roleService.queryAllRoles();
    }


    @RequestMapping("queryRolesByParams")
    @ResponseBody
    public Map<String, Object> queryRolesByParams(@RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer rows,
                                                  RoleQuery query) {
        query.setPageNum(page);
        query.setPageSize(rows);
        return roleService.queryForPage(query);
    }


    @RequestMapping("saveOrUpdateRole")
    @ResponseBody
    public ResultInfo saveOrUpdateRole(Role role) {
        roleService.saveOrUpdate(role);
        return success("操作成功");
    }

    @RequestMapping("deleteRole")
    @ResponseBody
    public ResultInfo deleteRole(Integer[] ids) {
        roleService.deleteBatch(ids);
        return success("操作成功");
    }

    @RequestMapping("queryModulePermissionByRoleId")
    @ResponseBody
    public List<PermissionDto> queryModulePermissionByRoleId(Integer roleId){
        return roleService.queryModulePermissionByRoleId(roleId);
    }

    @RequestMapping("doRoleGrant")
    @ResponseBody
    public ResultInfo doRoleGrant(Integer roleId, Integer[] moduleIds){

        roleService.doRoleGrant(roleId, moduleIds);
        return success("操作成功");
    }


}
