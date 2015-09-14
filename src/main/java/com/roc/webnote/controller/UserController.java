package com.roc.webnote.controller;

import com.roc.webnote.entity.User;
import com.roc.webnote.repository.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yp-tc-m-2795 on 15/9/13.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userDao;

    @RequestMapping(value = "/logins", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public String login(@ModelAttribute User user, HttpServletResponse response) {
        user = userDao.getUser(user);
        if (user != null) {
            // TODO Cookie & Session---查询到了登录的用户
            Cookie cookie = new Cookie("userCode", user.getCode());
            cookie.setMaxAge(30 * 60);
            cookie.setPath("/");

            response.addCookie(cookie);
            return "OK";
        }
        return "ERROR";
    }

    @RequestMapping(value = "/logins", method = RequestMethod.DELETE)
    @ResponseBody
    public String logout() {
        // TODO 删除session信息,但是现在没有session信息
        return "OK";
    }
}
