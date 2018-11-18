package com.shsxt.crm.proxy;

import com.shsxt.crm.annotations.RequestPermission;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.utils.AssertUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author xlf
 * @date 2018/7/26
 */
@Component
@Aspect
public class PermissionProxy {

    @Resource
    private HttpSession session;

    @Pointcut("@annotation(com.shsxt.crm.annotations.RequestPermission)")
    public void cut(){}

//    @Before(value="cut()")
//    public void before(){
//        System.out.println("前置通知.....");
//    }

    @Around(value = "cut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        Object result=null;
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        RequestPermission annotation = method.getAnnotation(RequestPermission.class);
        if(null!= annotation){
            String aclValue = annotation.aclValue();
//            System.out.println(aclValue);
//            System.out.println(session.getAttribute(CrmConstant.USER_PERMISSIONS));
            List<String> permissions = (List<String>) session.getAttribute(CrmConstant.USER_PERMISSIONS);
            AssertUtil.isTrue(CollectionUtils.isEmpty(permissions), "没有权限");
            AssertUtil.isTrue(!permissions.contains(aclValue), "没有权限");
        }
        result = pjp.proceed(); // 程序继续执行, 如果缺少这句话,程序只能执行一半就停止了!!
        //          /  前置
        // 环绕通知
        //          \  后置
        return result;
    }
}
