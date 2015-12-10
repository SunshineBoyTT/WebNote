package com.roc.webnote.controller;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.roc.webnote.entity.SocialUser;
import com.roc.webnote.entity.User;
import com.roc.webnote.lib.Util;
import com.roc.webnote.repository.mapper.SocialMapper;
import com.roc.webnote.repository.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by yp-tc-m-2795 on 15/9/13.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper   userDao;
    @Autowired
    private SocialMapper socialDao;

    @RequestMapping(value = "/logins", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public String login(@ModelAttribute User user, HttpServletResponse response) {
        user = userDao.getUser(user);
        if (user != null) {
            // TODO Cookie & Session---查询到了登录的用户
            Util.setCookie(response, user.getCode());
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

    /**
     * 去QQ...登录
     *
     * @return
     */
    @RequestMapping(value = "/social/qq", method = RequestMethod.GET)
    public String socialLoginDirect(HttpServletRequest request) {
        try {
            return "redirect:" + new Oauth().getAuthorizeURL(request);
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
        return "404";
    }

    /**
     * 处理openId,查询有没有记录,没有则添加
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/social/qqlogin", method = RequestMethod.GET)
    public String handleSocailLogin(HttpServletRequest request, HttpServletResponse response) {
        // TODO 获取OpenID
        String accessToken = null;
        String openID      = null;
        try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            long tokenExpireIn = 0L;


            if (accessTokenObj.getAccessToken().equals("")) {
//                我们的网站被CSRF攻击了或者用户取消了授权
//                做一些数据统计工作
                logger.info("没有获取到响应参数");
            } else {
                accessToken = accessTokenObj.getAccessToken();
                tokenExpireIn = accessTokenObj.getExpireIn();

                request.getSession().setAttribute("demo_access_token", accessToken);
                request.getSession().setAttribute("demo_token_expirein", String.valueOf(tokenExpireIn));

                // 利用获取到的accessToken 去获取当前用的openid --------
                OpenID openIDObj = new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();
            }

            SocialUser socialUser = new SocialUser();
            socialUser.setOpenID(openID);
            socialUser.setType("qq");
            // TODO 根据OpenID获取用户,没有就创建一个
            User user = userDao.getUserBySocial(socialUser);
            if (null == user) {
                // TODO 初始化用户,创建社会化账户,设置Cookie
                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                user = new User();
                user.setCode(UUID.randomUUID().toString());
                user.setUserName(userInfoBean.getNickname());// TODO 关联社会化账户信息
                user.setAvatar(userInfoBean.getAvatar().getAvatarURL100());
                userDao.insertUser(user);

                socialUser.setUserCode(user.getCode());
                socialDao.insertSocialUser(socialUser);

            }
            Util.setCookie(response, user.getCode());
        } catch (QQConnectException e) {
            logger.info("QQ login error: {}", e.getMessage());
        }

        return "redirect:/";

    }


}

