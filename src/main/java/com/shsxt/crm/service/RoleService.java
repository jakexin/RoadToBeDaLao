package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.ModuleMapper;
import com.shsxt.crm.dao.PermissionMapper;
import com.shsxt.crm.dao.RoleMapper;
import com.shsxt.crm.dao.UserRoleMapper;
import com.shsxt.crm.dto.PermissionDto;
import com.shsxt.crm.po.Module;
import com.shsxt.crm.po.Permission;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xlf on 2018/7/24.
 */
@Service
public class RoleService extends BaseService<Role> {
    @Resource
    private RoleMapper roleMapper;


    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    public List<Map> queryAllRoles(){
        return roleMapper.queryAllRoles();
    }

    public void saveOrUpdate(Role role){
        /***
         * 1. 检查参数
         * 2. 补全参数
         * 3. 判断添加还是修改
         * 4. 执行操作
         * */
        if(null==role.getId()){
            // 添加
            role.setCreateDate(new Date());
            role.setUpdateDate(new Date());
            role.setIsValid(1);

            AssertUtil.isTrue(roleMapper.save(role)<1, "角色添加失败");
        }else{
            role.setUpdateDate(new Date());
            AssertUtil.isTrue(roleMapper.update(role)<1, "角色更新失败");
        }
    }

    @Override
    public  Integer deleteBatch(Integer[] ids) throws DataAccessException {
        AssertUtil.isTrue(null==ids||ids.length==0,"请选择待删除记录!");

        /****
         * 级联删除 用户角色中间表
         * */
        for(Integer id : ids){
            Integer roles = userRoleMapper.queryRolesByRoleId(id);
            if(roles>0){
                AssertUtil.isTrue(userRoleMapper.deleteRoleByRoleId(id)<roles, "删除用户角色失败");
            }
        }
        return roleMapper.deleteBatch(ids);
    }

    public List<PermissionDto> queryModulePermissionByRoleId(Integer roleId){
        return roleMapper.queryModulePermissionByRoleId(roleId);
    }

    public void doRoleGrant(Integer roleId, Integer[] moduleIds) {
        /***
         * 1. 参数检测
         * 2. 模块的批量删除与添加
         * */
        // 检查roldId是否存在, 如果不存就停止操作
        AssertUtil.isTrue(null==roleId || null == roleMapper.queryById(roleId), "角色不存在");

        // 查询当前角色下所有的模块,有就进行删除
        Integer num = permissionMapper.queryPermissionsByRoleId(roleId);
        if(num>0){
            // 删除
            AssertUtil.isTrue(permissionMapper.deleteAllModulePermissionByRoleId(roleId)<num, "删除失败");
        }

        if(moduleIds.length>0){
            List<Permission> list = new ArrayList<Permission>();
            for (Integer moduleId : moduleIds){
                // 依次拿出每个moduleId, 然后通过moduleId获取对应的权限码值
                Module module = moduleMapper.queryById(moduleId);
                Permission permission = new Permission();
                permission.setRoleId(roleId);
                permission.setModuleId(moduleId);
                permission.setAclValue(module.getOptValue());//权限码值
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());

                list.add(permission);
            }
            AssertUtil.isTrue(permissionMapper.saveBatch(list)<list.size(), "添加权限失败");
        }
    }
}
