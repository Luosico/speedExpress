package com.luosico.security.smsCode;

import com.luosico.domain.User;
import com.luosico.domain.UserAuthority;
import com.luosico.service.SmsService;
import com.luosico.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/27
 */
@Component
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    public static final Logger logger = LoggerFactory.getLogger(SmsCodeAuthenticationProvider.class);

    @Autowired
    SmsService smsService;
    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;

        String phoneNumber = (String) authenticationToken.getPrincipal();
        String smsCode = (String) authenticationToken.getCredentials();

        //1、验证 redis 中该号码是否存在
        //2、验证号码对应的验证码是否正确
        //3、验证成功再加载用户信息
        if(!smsService.isExit(phoneNumber)){
            logger.info("redis中该号码: " + phoneNumber + " 不存在");
        }else if (!smsService.isCorrect(phoneNumber,smsCode)){
            logger.info(phoneNumber + " 验证码错误！");
        }else{
            logger.info(phoneNumber + " 验证成功");
            //加载用户信息
            User user = userService.selectUserByPhoneNumber(phoneNumber);
            if (user == null){
                return null;
            }
            SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken(new UserAuthority(user).getAuthorities(),authentication.getPrincipal(),authentication.getCredentials());
            return token;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        //只有这样判断，才会在自定义过滤器调用认证管理器认证时，只调用C自定义的认证方法，排除其他认证器
        //也就是仅让支持的 authenticationManager 认证
        return SmsCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
