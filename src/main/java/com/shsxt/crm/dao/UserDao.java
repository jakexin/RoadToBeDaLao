package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseDao;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.po.User;
import com.shsxt.crm.query.UserQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xlf
 * @date 2018/7/19
 */
@Repository
public interface UserDao extends BaseDao<User> {
    /**
     * 以用户名查询用户
     * @param userName
     * @return
     * @throws
     */
    User queryUserByName(String userName);
    Integer updateUserPwdById(User user);
    List<Map> queryAllCustomerManager();
    List<UserDto> queryUserByParams(UserQuery userQuery);
    UserDto queryUserById(Integer userId);
}
