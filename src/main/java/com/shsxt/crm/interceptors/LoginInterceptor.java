package com.shsxt.crm.interceptors;

import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xlf on 2018/7/23.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 通过cookie拿到用户id
        Integer useId = LoginUserUtil.releaseUserIdFromCookie(request);
        AssertUtil.isNotLogin(null==useId || null==userDao.queryById(useId), "用户未登陆");
        return true;
    }
}
