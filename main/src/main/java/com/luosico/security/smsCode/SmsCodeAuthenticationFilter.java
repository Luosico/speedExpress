package com.luosico.security.smsCode;

import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/27
 * <p>
 * 短信验证码验证过滤器
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final String phoneNumberParameter = "phoneNumber";
    public  final String smsCodeParameter = "smsCode";
    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/loginSmsCode", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String phoneNumber = obtainPhoneNumber(request);
            String smsCode = obtainSmsCode(request);
            logger.info("phoneNumber: " + phoneNumber);
            logger.info("smsCode: " + smsCode);

            if (phoneNumber == null) {
                phoneNumber = "";
            }

            if (smsCode == null){
                smsCode = "";
            }

            phoneNumber = phoneNumber.trim();
            smsCode = smsCode.trim();

            SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(phoneNumber, smsCode);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    @Nullable
    protected String obtainPhoneNumber(HttpServletRequest request) {
        return request.getParameter(this.phoneNumberParameter);
    }

    @Nullable
    protected String obtainSmsCode(HttpServletRequest request) {
        return request.getParameter(this.smsCodeParameter);
    }

    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}
