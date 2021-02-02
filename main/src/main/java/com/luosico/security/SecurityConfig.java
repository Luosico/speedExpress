package com.luosico.security;

import com.luosico.security.classic.UserAuthenticationProvider;
import com.luosico.security.handle.FailureHandle;
import com.luosico.security.handle.SuccessHandle;
import com.luosico.security.smsCode.SmsCodeAuthenticationFilter;
import com.luosico.security.smsCode.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;


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
    @Autowired
    SmsCodeAuthenticationProvider smsCodeAuthenticationProvider;

    @Qualifier("authenticationManagerBean")
    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * 设置加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //自定义认证器
        auth.authenticationProvider(authenticationProvider);
        auth.authenticationProvider(smsCodeAuthenticationProvider);
    }

    //会屏蔽页面引用的 css、js等静态资源
    @Override
    public void configure(WebSecurity web) throws Exception {
        //允许静态资源访问，一定要写完整路径
        //web.ignoring().antMatchers("/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //添加自定义过滤器
        http.addFilterBefore(smsCodeAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class);

        http
                .authorizeRequests() //授权配置
                //这些路径不需要身份认证
                .antMatchers("/login", "/loginSmsCode","/smsCode","/css/**","/js/**")
                .permitAll()
                //其他路径需要身份认证
                .anyRequest().authenticated()
                .and()
                //loginPage指定URL来自定义登录界面
                .formLogin()//登录表单
                .loginPage("/login") //登录页面 URL
                //.loginProcessingUrl("/login") //登录信息提交url
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

    //这个必须重写，才能使用AuthenticationManager，在成员变量注入进来，再注入过滤器中
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 自定义登录认证过滤器
     */
    @Bean
    public SmsCodeAuthenticationFilter smsCodeAuthenticationFilter() {
        SmsCodeAuthenticationFilter filter = new SmsCodeAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(successHandle);
        filter.setAuthenticationFailureHandler(failureHandle);
        filter.setFilterProcessesUrl("/loginSmsCode");
        return filter;
    }
}
