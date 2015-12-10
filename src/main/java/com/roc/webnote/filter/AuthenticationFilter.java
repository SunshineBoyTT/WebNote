package com.roc.webnote.filter;

import com.roc.webnote.entity.User;
import com.roc.webnote.lib.Util;
import com.roc.webnote.repository.impl.UserDao;
import com.roc.webnote.repository.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by yp-tc-m-2795 on 15/9/11.
 */
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger     logger  = LoggerFactory.getLogger(AuthenticationFilter.class);
    private              UserMapper userDao = new UserDao();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO 根据 URL 和 Cookie 判断请求是否合法
        // TODO 第一期只控制登录状态:未登录跳转到登录
        // TODO 在这里操作可以统一的控制权限,谁可以不可以进行想要的操作/请求
        String                  uri            = request.getRequestURI();
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request);
        if (!uri.startsWith("/resources")) {
            logger.info("Test Filter,URI: {}", uri);


            String userCode = getValue("userCode", request.getCookies());
            System.out.println("Test Filter,URI: " + uri + ":" + userCode);

            // Cookie更新
            if (!StringUtils.isEmpty(userCode)) {
                Util.setCookie(response, userCode);
                User user = userDao.getUserByCode(userCode);
                request.setAttribute("user", user);

                requestWrapper.setAttribute("user", user);
            }

        } else {
            // TODO 静态文件设置缓存
            response.setHeader("Cache-control", "public, max-age=72000");
        }
        filterChain.doFilter(requestWrapper, response);
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

    public class ParameterRequestWrapper extends HttpServletRequestWrapper {

        private Map<String, String[]> params = new HashMap<String, String[]>();

        @SuppressWarnings("unchecked")
        public ParameterRequestWrapper(HttpServletRequest request) {
            // 将request交给父类，以便于调用对应方法的时候，将其输出，其实父亲类的实现方式和第一种new的方式类似
            super(request);
            //将参数表，赋予给当前的Map以便于持有request中的参数
            this.params.putAll(request.getParameterMap());
            this.modifyParameterValues();
        }

        //重载一个构造方法
        public ParameterRequestWrapper(HttpServletRequest request, Map<String, Object> extendParams) {
            this(request);
            addAllParameters(extendParams);//这里将扩展参数写入参数表
        }

        public void modifyParameterValues() {//将parameter的值去除空格后重写回去
            Set<String>      set = params.keySet();
            Iterator<String> it  = set.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String[] values = params.get(key);
                values[0] = values[0].trim();
                params.put(key, values);
            }
        }

        @Override
        public String getParameter(String name) {//重写getParameter，代表参数从当前类中的map获取
            String[] values = params.get(name);
            if (values == null || values.length == 0) {
                return null;
            }
            return values[0];
        }

        public String[] getParameterValues(String name) {//同上
            return params.get(name);
        }

        public void addAllParameters(Map<String, Object> otherParams) {//增加多个参数
            for (Map.Entry<String, Object> entry : otherParams.entrySet()) {
                addParameter(entry.getKey(), entry.getValue());
            }
        }


        public void addParameter(String name, Object value) {//增加参数
            if (value != null) {
                if (value instanceof String[]) {
                    params.put(name, (String[]) value);
                } else if (value instanceof String) {
                    params.put(name, new String[]{(String) value});
                } else {
                    params.put(name, new String[]{String.valueOf(value)});
                }
            }
        }
    }
}
