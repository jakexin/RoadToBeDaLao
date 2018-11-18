package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseDao;
import com.shsxt.crm.po.Module;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Repository
public interface ModuleMapper extends BaseDao<Module>{

    public List<Map> queryParentModuleByGrade (Integer grade);

    public Module queryByModuleName(String moduleName);

    public Module queryByOptValue(String optValue);

    public Integer deleteModulesByOptValue(String optValue);
}