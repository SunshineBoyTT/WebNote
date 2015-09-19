package com.roc.webnote.repository.impl;

import com.roc.webnote.entity.SocialUser;
import com.roc.webnote.entity.User;
import com.roc.webnote.repository.BaseDao;
import com.roc.webnote.repository.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * Created by yp-tc-m-2795 on 15/9/13.
 */
@Repository
public class UserDao extends BaseDao implements UserMapper {

    @Override
    public User getUser(User user) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            return userMapper.getUser(user);
        }
    }

    @Override
    public User getUserByCode(String userUuid) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            return userMapper.getUserByCode(userUuid);
        }
    }

    @Override
    public User getUserBySocial(SocialUser socialUser) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            return userMapper.getUserBySocial(socialUser);
        }
    }

    @Override
    public void insertUser(User user) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.insertUser(user);
        }
    }
}
