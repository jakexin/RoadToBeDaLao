package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.Module;
import com.shsxt.crm.query.ModuleQuery;
import com.shsxt.crm.service.ModuleService;
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
 * @date 2018/7/26
 */
@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;
    @RequestMapping("index")
    public String index(){
        return "module";
    }

    @RequestMapping("queryModulesByParams")
    @ResponseBody
    public Map<String, Object> queryModulesByParams(@RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer rows,
                                                    ModuleQuery query){
        query.setPageNum(page);
        query.setPageSize(rows);
        return moduleService.queryForPage(query);
    }

    @RequestMapping("queryParentModuleByGrade")
    @ResponseBody
    public List<Map> queryParentModuleByGrade(Integer grade){
        return moduleService.queryParentModuleByGrade(grade);
    }

    @RequestMapping("saveOrUpdateModule")
    @ResponseBody
    public ResultInfo saveOrUpdateModule(Module module){
        moduleService.saveOrUpdate(module);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("deleteModulesByOptValue")
    @ResponseBody
    public ResultInfo deleteModulesByOptValue(String optValue){
        moduleService.deleteModulesByOptValue(optValue);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

}
