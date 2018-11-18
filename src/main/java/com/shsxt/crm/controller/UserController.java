package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.exceptions.ParamsException;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserInfo;
import com.shsxt.crm.po.User;
import com.shsxt.crm.query.SaleChanceQuery;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.service.PermissionService;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 *
 * @author xlf
 * @date 2018/7/19
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {


    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    @RequestMapping("index")
    public String index(){
        return "user";
    }

    @RequestMapping("login")
    @ResponseBody
    public ResultInfo login(String userName, String password, HttpSession session) {
        UserInfo userInfo = userService.checkLogin(userName, password);
        /***
         * 通过userId查询所有模块权限
         * */
        // 查询用户
        User user = userService.queryUserByName(userName);
        // 通过用户id查模块权限
        List<String> permissionsList = permissionService.queryAllModuleAclValueByUserId(user.getId());
        // 放入作用域
        //model.addAttribute(CrmConstant.USER_PERMISSIONS, permissionsList);
        session.setAttribute(CrmConstant.USER_PERMISSIONS, permissionsList);
        return success("登陆成功", userInfo);
    }

    @RequestMapping("updateUserPwd")
    @ResponseBody
    public ResultInfo updateUserPwd(String oldPassword,
                                    String newPassword,
                                    String confirmPassword,
                                    HttpServletRequest request) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        userService.updateUserPwdById(oldPassword, newPassword, confirmPassword, userId);
        return success("密码修改成功");
    }

    @RequestMapping("queryCustomerManagers")
    @ResponseBody
    public List<Map> queryAllCustomerManager(){
        return userService.queryAllCustomerManager();
    }


    @RequestMapping("queryUserByParams")
    @ResponseBody
    public Map<String,Object> queryUserByParams( @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer rows,
                                     UserQuery query){
        query.setPageNum(page);
        query.setPageSize(rows);
        return userService.queryUserForPage(query);
    }

    @RequestMapping("saveOrUpdateUser")
    @ResponseBody
    public ResultInfo saveOrUpdateUser(UserDto userDto){
        userService.saveOrUpdateUser(userDto);
        return success("添加成功");
    }

    @RequestMapping("deleteUser")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteUser(ids);
        return success("删除成功");
    }


}
