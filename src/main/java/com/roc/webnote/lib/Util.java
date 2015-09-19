package com.roc.webnote.lib;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 字符编码
 * Created by yp-tc-m-2795 on 15/9/15.
 */
public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static void sendEmail(SimpleMailMessage emailEntity) {
        Email email = new SimpleEmail();
        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("1373462009", "chen0307"));
        email.setSSLOnConnect(true);

        try {
            email.setFrom("1373462009@qq.com");
            email.setSubject("TestMail");
            email.setMsg("This is a test mail ... :-)");
            email.addTo("745656593@qq.com");
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }

    public static void setCookie(HttpServletResponse response, String userCode) {
        Cookie cookie = new Cookie("userCode", userCode);
        cookie.setMaxAge(30 * 60);
        cookie.setPath("/");

        response.addCookie(cookie);
    }
}
