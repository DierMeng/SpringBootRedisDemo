package com.glorze.dao;

import com.glorze.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户持久层
 *
 * @ClassName: UserDao
 * @author: glorze.com_高老四
 * @since: 2018/11/27 16:35
 */
@Repository
public interface UserDao {

    /**
     * 获取单个用户
     * @Title: getUser
     * @param id
     * @return com.glorze.entity.User
     * @author: 高老四博客
     * @since: 2018/11/27 16:37
     */
    User getUser(Long id);

    /**
     * 保存用户
     * @Title: insertUser
     * @param user
     * @return int
     * @author: 高老四博客
     * @since: 2018/11/27 16:37
     */
    int insertUser(User user);

    /**
     * 修改用户
     * @Title: updateUser
     * @param user
     * @return int
     * @author: 高老四博客
     * @since: 2018/11/27 16:37
     */
    int updateUser(User user);

    /**
     * 查询用户，指定MyBatis的参数名称
     * @Title: findUsers
     * @param userName
     * @param  note
     * @return java.util.List<com.glorze.entity.User>
     * @author: 高老四博客
     * @since: 2018/11/27 16:38
     */
    List<User> findUsers(@Param("userName") String userName, @Param("note") String note);

    /**
     * 删除用户
     * @Title: deleteUser
     * @param id
     * @return int
     * @author: 高老四博客
     * @since: 2018/11/27 16:38
     */
    int deleteUser(Long id);
}
