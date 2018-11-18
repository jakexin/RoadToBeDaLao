package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author xlf
 * @date 2018/7/19
 */
@Controller
public class MainController extends BaseController{
    @Resource
    private UserService userService;

    @RequestMapping("main")
    public String index(HttpServletRequest request, HttpSession session){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        UserDto userDto = userService.queryUserById(userId);
        session.setAttribute("user", userDto);
        return "main";
    }
}
