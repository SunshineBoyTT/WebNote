package com.roc.webnote.repository.impl;

import com.roc.webnote.entity.User;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by yp-tc-m-2795 on 15/9/13.
 */
public class UserDaoTest {

    @Test
    public void testGetUser() throws Exception {
        UserDao userDao = new UserDao();
        User    user    = new User();
        user.setUserName("cshijiel");
        user.setPassword("1234");
        user = userDao.getUser(user);
        System.out.println(user.getCode());
    }

    @Test
    public void testGetUser2() throws Exception {
        UserDao userDao = new UserDao();
        User    user    = userDao.getUserByCode("12121");
        System.out.println(user.getUserName());
    }

    @Test
    public void initUser() {
        UserDao userDao = new UserDao();
        User    user    = new User();
        user.setCode(UUID.randomUUID().toString());
        user.setUserName("America");
        user.setPassword("********");
        userDao.insertUser(user);
    }
}