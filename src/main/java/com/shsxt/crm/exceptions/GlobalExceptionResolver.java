package com.shsxt.crm.exceptions;

import com.alibaba.fastjson.JSON;
import com.shsxt.crm.model.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * Created by xlf on 2018/7/20.
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler, Exception ex) {
        ModelAndView mv = getDefaultModelAndView(request);

        // 拦截未登陆异常
        // 无论时请求页面报错还是请求json,都会重新跳转到登陆页
        if(ex instanceof AuthException){
            AuthException ax = (AuthException) ex;
            mv.addObject("errorCode", ax.getCode());
            mv.addObject("errorMsg", ax.getMsg());
            return mv;
        }


        /***
         * 判断是返回普通页面还是json数据
         *                                                          /  有  --> 返回json数据
         *  通过 handler -> method --反射--> 方式上的注解ResponseBody
         *                                                          \ 没有 --> 返回页面
         * */

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            ResponseBody annotation = method.getAnnotation(ResponseBody.class);
            if (null != annotation) {
                ResultInfo resultInfo = new ResultInfo();
                // 返回的是json数据
                if (ex instanceof ParamsException) {
                    ParamsException e = (ParamsException) ex;
                    resultInfo.setCode(e.getCode());
                    resultInfo.setMsg(e.getMsg());
                }
                // 输出客户端json字符串
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset=utf-8");

                PrintWriter writer = null;
                try {
                    writer = response.getWriter();//获取输出流
                    //设置输出编码
                    writer.write(JSON.toJSONString(resultInfo));
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (null != writer) {
                        writer.close();
                    }
                }
                return null;
            } else {
                // 返回页面
                if (ex instanceof ParamsException) {
                    ParamsException e = (ParamsException) ex;
                    mv.addObject("errorCode", e.getCode());
                    mv.addObject("errorMsg", e.getMsg());
                }
                return mv;
            }
        } else {
            // 返回页面
            return mv;
        }
    }

    private ModelAndView getDefaultModelAndView(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");
        mv.addObject("errorCode", 300);
        mv.addObject("errorMsg", "请求失败");
        mv.addObject("ctx", request.getContextPath());
        mv.addObject("uri", request.getRequestURI());//请求的url
        return mv;
    }
}
