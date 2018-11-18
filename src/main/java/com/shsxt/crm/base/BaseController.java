package com.shsxt.crm.base;

import com.shsxt.crm.model.ResultInfo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author xlf
 * @date 2018/7/19
 */
public class BaseController {
    @ModelAttribute
    public void preMethod(Model model, HttpServletRequest request){
        model.addAttribute("ctx", request.getContextPath());
    }

    public ResultInfo success(String msg){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public ResultInfo success(String msg, Object result){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg(msg);
        resultInfo.setResult(result);
        return resultInfo;
    }
}
