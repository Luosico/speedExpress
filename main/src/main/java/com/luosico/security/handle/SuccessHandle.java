package com.luosico.security.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luosico.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/13
 */
@Component
public class SuccessHandle implements AuthenticationSuccessHandler {

    public static final Logger logger = LoggerFactory.getLogger(SuccessHandle.class);

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (logger.isDebugEnabled()) {
            logger.debug("username: " + authentication.getPrincipal());
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("status", "ok");
        map.put("role", getAuthority(authentication));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);

        httpServletResponse.setStatus(200);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/plain");

        //帐号密码登录
        if (httpServletRequest.getParameter("username") != null) {
            //添加cookie
            userService.loginSuccessAddCookie(httpServletResponse,"pwd", (String) authentication.getPrincipal());
        } else {
            //手机验证码登录
            userService.loginSuccessAddCookie(httpServletResponse,"phone", (String) authentication.getPrincipal());
        }
        PrintWriter out = httpServletResponse.getWriter();
        out.write(json);
        out.flush();
        out.close();
    }

    /**
     * 获取身份
     *
     * @param authentication
     * @return
     */
    private String getAuthority(Authentication authentication) {
        Iterator iterator = authentication.getAuthorities().iterator();

        SimpleGrantedAuthority simpleGrantedAuthority = (SimpleGrantedAuthority) iterator.next();
        String authority = simpleGrantedAuthority.getAuthority();
        String result = "";
        switch (authority) {
            case "ROLE_USER":
                result = "user";
                break;
            case "ROLE_COURIER":
                result = "courier";
                break;
            case "ROLE_ADMIN":
                result = "admin";
                break;
        }
        return result;
    }
}
