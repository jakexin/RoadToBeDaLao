package com.shsxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.crm.base.BaseQuery;
import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.dao.UserRoleMapper;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.model.UserInfo;
import com.shsxt.crm.po.User;
import com.shsxt.crm.po.UserRole;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by xlf on 2018/7/19.
 */
@Service
public class UserService extends BaseService<User> {

    // @Autowired 是spring框架提供
    // @Resource 是jdk提供
    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */
    public void updateUserPwdById(String oldPassword,
                                  String newPassword,
                                  String confirmPassword, Integer userId){
        /***
         * 1. 检查参数
         * 2. 比对旧密码是否正确
         *      思路: 通过idStr 获取 ->  用户id  -> 通过id查询用户  ->  获取旧密码
         * 3. 修改密码, 存入加密密码
         * */
        // 检查参数
        checkUpdateUserParams(oldPassword, newPassword, confirmPassword);

        // 取出用户
        User user = userDao.queryById(userId);
        AssertUtil.isTrue(null == user, "用户未登陆或者不存在");

        //比对密码
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPassword)), "旧密码不正确");

        // 修改密码
        newPassword = Md5Util.encode(newPassword);// 加密密码
        user.setUserPwd(newPassword);//设置新密码

        AssertUtil.isTrue(userDao.updateUserPwdById(user)<1, "密码修改失败");

    }

    private void checkUpdateUserParams(String oldPassword,
                                       String newPassword,
                                       String confirmPassword) {
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword), "旧密码为空");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword), "新密码为空");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword), "确认密码为空");
        AssertUtil.isTrue(!newPassword.equals(confirmPassword), "两次密码不一致");
    }

    /**
     * 登陆
     * @param userName
     * @param password
     * @return
     */
    public UserInfo checkLogin(String userName, String password){
        /**
         * 1. 校验参数
         * 2. 通过用户名,查询用户
         * 3. 判断密码是否一致
         * 4. 返回登录信息
         * */
        // 检查参数
        checkLoginParams(userName, password);
        // 查询用户
        User user = userDao.queryUserByName(userName);
        AssertUtil.isTrue(null == user, "用户已注销或不存在");
        // 比如密码和数据库密码是否一致
        // 如果一致代表登陆正确, 否则失败
        // 先把用户传入的密码加密后, 与数据库对比

        AssertUtil.isTrue(!Md5Util.encode(password).equals(user.getUserPwd()),"用户名或者密码不正确");

        // 准备返回信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(user.getUserName());
        userInfo.setTrueName(user.getTrueName());
        userInfo.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        return userInfo;
    }

    private void checkLoginParams(String userName, String password) {
//        if(StringUtils.isBlank(userName)){
//            System.out.println("用户名为空");
//        }
//        if(StringUtils.isBlank(password)){
//            System.out.println("密码为空");
//        }

        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名为空");
        AssertUtil.isTrue(StringUtils.isBlank(password), "密码为空");
    }


    //查询所有客户经理
    public List<Map> queryAllCustomerManager(){
        return userDao.queryAllCustomerManager();
    }

    public Map<String,Object> queryUserForPage(UserQuery baseQuery) throws DataAccessException {
        PageHelper.startPage(baseQuery.getPageNum(),baseQuery.getPageSize());
        List<UserDto> entities=userDao.queryUserByParams(baseQuery);
        /***
         * 2,3,4
         * -> Integer[]
         * */
        if(!CollectionUtils.isEmpty(entities)){
            for(UserDto userDto : entities){
                String roleIdsStr = userDto.getRoleIdsStr();
                if(null!= roleIdsStr && "" != roleIdsStr){
                    String[] splitIds = roleIdsStr.split(",");
                    System.out.println(splitIds);
                    for (int i=0; i<splitIds.length; i++){
                        userDto.getRoleIds().add(Integer.parseInt(splitIds[i]));
                    }
                }
            }
        }

        PageInfo<UserDto> pageInfo=new PageInfo<UserDto>(entities);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }

    /***
     * 添加或者更新用户
     * */
    public void saveOrUpdateUser(UserDto userDto) {
        /***
         * 1. 检查参数
         * 2. 判断添加或者更新
         * 3. 参数补全
         *      初始密码: 123456
         * 4. 执行操作
         *      角色更新: 思路
         *          无论之前有哪些角色, 全部删除;
         *          重新添加新的角色
         * 5. 返回数据
         * */
        checkUserParams(userDto.getUserName(), userDto.getTrueName(), userDto.getPhone(), userDto.getEmail());

        Integer userId = userDto.getId();

        //User newUser = new User();
        Integer newUserId = null;



        if(null == userId){
            // 添加
            // 用户名唯一校验
            //查询的用户
            User user = userDao.queryUserByName(userDto.getUserName());
            AssertUtil.isTrue(null != user, "用户名重复");

            userDto.setUserPwd(Md5Util.encode("123456"));// 加密密码
            userDto.setIsValid("1");// 有效
            userDto.setCreateDate(new Date());
            userDto.setUpdateDate(new Date());
            AssertUtil.isTrue(userDao.save(userDto)<1, "用户添加失败");
            /***
             * 获取新添加用户的ID
             * */
            User newUser = userDao.queryUserByName(userDto.getUserName());
            newUserId = newUser.getId();
        }else{
            // 更新
            //查询的用户
            User user = userDao.queryById(userDto.getId());
            // 用户名不允许修改
            AssertUtil.isTrue(!userDto.getUserName().equals(user.getUserName()), "不允许修改用户名");
            userDto.setUpdateDate(new Date());
            AssertUtil.isTrue(userDao.update(userDto)<1, "用户更新失败");
            newUserId = user.getId();
        }

        // 分配角色
        manageRelation(userDto.getRoleIds(), newUserId);
    }

    private void manageRelation(List<Integer> roleIds, Integer userId) {
        /***
         * 分配用户角色
         * 1. 查询该用户是否有角色
         *   如果有角色,删除之前的所有角色
         *   如果没有,直接添加
         * 2. 添加角色
         * */
        // 1. 查询该用户是否有角色
        Integer roleNum = userRoleMapper.queryRolesByUserId(userId);
        if(0!=roleNum){
            AssertUtil.isTrue(userRoleMapper.deleteRoleByUserId(userId)<roleNum, "删除角色失败");
        }

        /***
         * 判断有角色才添加
         * */
        if(null!=roleIds && roleIds.size()>0){
            List<UserRole> list = new ArrayList<UserRole>();
            for(Integer roleId : roleIds){
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                list.add(userRole);//添加到集合
            }
            AssertUtil.isTrue(userRoleMapper.saveBatch(list)<list.size(), "用户角色添加失败");
            //userRoleMapper.save()
        }

    }

    private void checkUserParams(String userName, String trueName, String phone, String email) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名为空");
        AssertUtil.isTrue(StringUtils.isBlank(trueName), "真实姓名为空");
        AssertUtil.isTrue(StringUtils.isBlank(phone), "电话为空");
        AssertUtil.isTrue(StringUtils.isBlank(email), "邮箱为空");

    }


    public void deleteUser(Integer[] ids){
        if(null!=ids && ids.length>0){
            for(Integer id: ids){
                /***
                 * 删除用户,删除对应角色
                 * */
                AssertUtil.isTrue(userDao.delete(id)<1, "用户删除失败");
                // 先查询用户有没有角色
                // 如果有就删除, 没有就不用删了
                Integer roles = userRoleMapper.queryRolesByUserId(id);
                if(roles>0){
                    AssertUtil.isTrue(userRoleMapper.deleteRoleByUserId(id)<roles, "用户角色删除失败");
                }
            }
        }
    }

    public User queryUserByName(String userName){
        return  userDao.queryUserByName(userName);
    }

    public UserDto queryUserById(Integer userId){
        return userDao.queryUserById(userId);
    }

}
