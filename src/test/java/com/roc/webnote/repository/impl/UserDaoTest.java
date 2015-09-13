package com.roc.webnote.repository.impl;

import com.roc.webnote.entity.User;
import org.junit.Test;

/**
 * Created by yp-tc-m-2795 on 15/9/13.
 */
public class UserDaoTest {

    @Test
    public void testGetUser() throws Exception {
        UserDao userDao = new UserDao();
        User    user    = new User();
        user.setUserName("cshijiel");
        user.setPassword("password");
        user = userDao.getUser(user);
        System.out.println(user.getCode());
    }

    @Test
    public void testGetUser2() throws Exception {
        UserDao userDao = new UserDao();
        User    user    = userDao.getUserByUuid("12121");
        System.out.println(user.getUserName());
    }
}