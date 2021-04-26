package com.luosico.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器配置类
 *
 * @Author: luo kai fa
 * @Date: 2021/4/26
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    ValidateCookieInterceptor validateCookieInterceptor;

    /**
     * 拦截路径
     */
    List<String> pathPatterns = new ArrayList<>();

    public InterceptorConfig() {
        pathPatterns.add("/user/**");
        pathPatterns.add("/courier/**");
        pathPatterns.add("/admin/**");
        pathPatterns.add("/common/**");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validateCookieInterceptor).addPathPatterns(pathPatterns);
    }
}
