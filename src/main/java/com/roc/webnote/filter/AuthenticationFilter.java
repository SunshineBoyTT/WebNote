package com.roc.webnote.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yp-tc-m-2795 on 15/9/11.
 */
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO 根据 URL 和 Cookie 判断请求是否合法
        // TODO 第一期只控制登录状态:未登录跳转到登录
        String uri = request.getRequestURI();
        if (!uri.startsWith("/resources/")) {
            System.out.println("Test Filter,URI: " + uri);
        } else {
            response.setHeader("Cache-control", "max-age=[72000]");
        }
        filterChain.doFilter(request, response);
    }
}
