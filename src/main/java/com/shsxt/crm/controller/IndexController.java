package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xlf on 2018/7/19.
 */
@Controller
public class IndexController extends BaseController{

    @RequestMapping("index")
    public String index(){
        //model.addAttribute("ctx", request.getContextPath());
        return "index";
    }

//    @ModelAttribute
//    public void preMethod(Model model, HttpServletRequest request){
//        model.addAttribute("ctx", request.getContextPath());
//    }
}
