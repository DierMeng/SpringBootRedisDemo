package com.glorze.service.impl;

import com.glorze.dao.UserDao;
import com.glorze.entity.User;
import com.glorze.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户操作业务层实现类
 *
 * @ClassName: UserServiceImpl
 * @author: glorze.com_高老四
 * @since: 2018/11/27 16:43
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao = null;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "redisCache", key = "'redis_user_'+#id")
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    /**
     * MyBatis回填主键用户编号,将其缓存
     * #ressult代表返回的结果对象
     * @Title: insertUser
     * @param user
     * @return com.glorze.entity.User
     * @author: 高老四博客
     * @since: 2018/11/27 16:45
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "redisCache", key = "'redis_user_'+#result.id")
    public User insertUser(User user) {
        userDao.insertUser(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "redisCache", condition = "#result != 'null'", key = "'redis_user_'+#id")
    public User updateUserName(Long id, String userName) {
        // 调用getUser方法,缓存注解会失效所以会继续执行SQL,将查询到数据库最新数据
        User user = this.getUser(id);
        if (user == null) {
            return null;
        }
        user.setUserName(userName);
        userDao.updateUser(user);
        return user;
    }

    @Override
    public List<User> findUsers(String userName, String note) {
        return userDao.findUsers(userName, note);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "redisCache", key = "'redis_user_'+#id", beforeInvocation = false)
    public int deleteUser(Long id) {
        return userDao.deleteUser(id);
    }
}
