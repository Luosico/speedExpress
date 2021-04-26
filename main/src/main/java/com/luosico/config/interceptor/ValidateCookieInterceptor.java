package com.luosico.config.interceptor;

import com.luosico.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 根据 cookie鉴别用户身份
 * 失败则重定向至登录页面
 *
 * @Author: luo kai fa
 * @Date: 2021/4/26
 */
@Component
public class ValidateCookieInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    //Controller处理之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (userService.UidIsCorrect(request.getCookies())) {
            return true;
        }
        response.sendRedirect("/logout");
        return false;
    }

    //Controller 处理之后的 ModelAndView 对象进行操作
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //DispatcherServlet 渲染了对应的视图之后执行, 主要用来进行资源清理
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
