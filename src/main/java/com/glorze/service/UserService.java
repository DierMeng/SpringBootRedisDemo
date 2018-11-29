package com.glorze.service;

import com.glorze.entity.User;

import java.util.List;

/**
 * 用户操作业务层
 *
 * @InterfaceName: UserService
 * @author: glorze.com_高老四
 * @since: 2018/11/27 16:39
 */
public interface UserService {

    /**
     * 获取单个用户
     * @Title: getUser
     * @param id
     * @return User
     * @author: 高老四博客
     * @since: 2018/11/27 16:40
     */
    User getUser(Long id);

    /**
     * 保存用户
     * @Title: insertUser
     * @param user
     * @return com.glorze.entity.User
     * @author: 高老四博客
     * @since: 2018/11/27 16:40
     */
    User insertUser(User user);

    /**
     * 修改用户，指定MyBatis的参数名称
     * @Title: updateUserName
     * @param id
     * @param userName
     * @return com.glorze.entity.User
     * @author: 高老四博客
     * @since: 2018/11/27 16:40
     */
    User updateUserName(Long id, String userName);

    /**
     * 查询用户，指定MyBatis的参数名称
     * @Title: findUsers
     * @param userName
     * @param note
     * @return java.util.List<com.glorze.entity.User>
     * @author: 高老四博客
     * @since: 2018/11/27 16:41
     */
    List<User> findUsers(String userName, String note);

    /**
     * 删除用户
     * @Title: deleteUser
     * @param id
     * @return int
     * @author: 高老四博客
     * @since: 2018/11/27 16:41
     */
    int deleteUser(Long id);
}
