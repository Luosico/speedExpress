package com.luosico.security.classic;


import com.luosico.domain.UserAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * @Author: luo kai fa
 * @Date: 2021/1/14
 * <p>
 * 用户登录验证
 * <p>
 * 当存在多个 AuthenticationProvider 时，有一个符合就通过验证
 */

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    public static final Logger logger = LoggerFactory.getLogger(UserAuthenticationProvider.class);

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserServiceSecurity userServiceSecurity;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //请求的用户名
        String username = authentication.getName();
        logger.info("username[ " + username + " ] try authenticate");

        UserAuthority userAuthority = (UserAuthority) userServiceSecurity.loadUserByUsername(username);

        if (userAuthority == null) {
            logger.info("用户不存在");
            throw new UsernameNotFoundException("[" + username + "]不存在");
        } else if (!passwordEncoder.matches((String) authentication.getCredentials(), userAuthority.getPassword())) {
            logger.info("[" + username + "]密码不正确");
            throw new BadCredentialsException("[" + username + "]密码不正确");
        } else {
            //验证成功
            logger.info("username[ " + username + " ] authenticate success!");
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), userAuthority.getAuthorities());
            return token;
        }
    }

    /**
     * 是否支持传递给 authenticate()方法
     */
    @Override
    public boolean supports(Class<?> aClass) {
        //参数必须为 UsernamePasswordAuthenticationToken 类或者其子类的字节码，此参数又是由UsernamePasswordAuthenticationFilter 里传过来的
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
