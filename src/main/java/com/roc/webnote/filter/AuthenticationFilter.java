package com.roc.webnote.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yp-tc-m-2795 on 15/9/11.
 */
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO 根据 URL 和 Cookie 判断请求是否合法
        // TODO 第一期只控制登录状态:未登录跳转到登录
        // TODO 在这里操作可以统一的控制权限,谁可以不可以进行想要的操作/请求
        String uri = request.getRequestURI();
        if (!uri.startsWith("/resources/")) {
            logger.info("Test Filter,URI: {}", uri);
            String userUuid = getValue("user", request.getCookies());
            request.setAttribute("user", userUuid);
        } else {
            // TODO 静态文件设置缓存
            response.setHeader("Cache-control", "public, max-age=72000");
        }
        filterChain.doFilter(request, response);
    }


    /**
     * 从Cookie中根据Key获取Value
     *
     * @param key
     * @param cookies
     * @return
     */
    private static String getValue(String key, Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
