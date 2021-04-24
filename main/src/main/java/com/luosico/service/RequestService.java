package com.luosico.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求处理
 *
 * @Author: luo kai fa
 * @Date: 2021/4/24
 */
@Service
public class RequestService {

    @Autowired
    UtilService utilService;

    /**
     * 登录成功，设置cookie, 并存入redis
     * @param response
     * @param username
     */
    public void loginSuccessAddCookie(HttpServletResponse response, String username) {
        String uid = UtilService.getCharAndNum(8);
        Cookie cookie = new Cookie("uid", uid);
        //作用路径
        cookie.setPath("/");
        //过期时间 秒
        cookie.setMaxAge(60 * 30);
        response.addCookie(cookie);

        //存入redis
        utilService.setUidAndUsername(uid,username);
    }

    /**
     * 根据 cookie 的 uid 获取当前请求的 username
     * @param cookies
     * @return username
     */
     public String getUsernameByCookie(Cookie[] cookies){
         if (cookies != null && cookies.length > 0) {
             for (Cookie cookie : cookies) {
                 if ("uid".equals(cookie.getName())) {
                     String uid = cookie.getValue();
                     String username = utilService.getUsernameByUid(uid);
                     return username;
                 }
             }
         }
        return null;
     }
}
