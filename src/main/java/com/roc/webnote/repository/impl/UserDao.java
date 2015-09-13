package com.roc.webnote.repository.impl;

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
    public User getUserByUuid(String userUuid) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            return userMapper.getUserByUuid(userUuid);
        }
    }
}
