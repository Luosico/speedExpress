package com.luosico.security;

import com.luosico.security.authenticationProvider.UserAuthenticationProvider;
import com.luosico.security.handle.FailureHandle;
import com.luosico.security.handle.SuccessHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/13
 */
@Configuration
@EnableWebSecurity //启用方法安全设置
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SuccessHandle successHandle;

    @Autowired
    FailureHandle failureHandle;

    @Autowired
    UserAuthenticationProvider authenticationProvider;

    /**
     * 设置加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    //会屏蔽页面引用的 css、js等静态资源
    @Override
    public void configure(WebSecurity web) throws Exception {
        //允许静态资源访问，一定要写完整路径
        web.ignoring().antMatchers("/css/**","/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //授权配置
                //这些路径不需要身份认证
                .antMatchers("/login.html", "/error.html","/css/**","/js/**")
                .permitAll()
                //其他路径需要身份认证
                .anyRequest().authenticated()
                .and()
                //loginPage指定URL来自定义登录界面
                .formLogin()//登录表单
                .loginPage("/login.html") //登录页面 URL
                .loginProcessingUrl("/login") //登录信息提交url
                .permitAll()//登录成功后有权限访问所有界面
                .successHandler(successHandle)
                .failureHandler(failureHandle)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
        //关闭csrf跨域攻击防御
        //如果不关闭，需要在请求接口的时候加入csrfToken才行
        http.csrf().disable();
    }
}
