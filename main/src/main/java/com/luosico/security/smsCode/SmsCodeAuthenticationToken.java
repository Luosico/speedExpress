package com.luosico.security.smsCode;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/27
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    public static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    //相当于帐号
    private final Object principal;
    private Object credentials;

    //从request获取信息，待验证
    public SmsCodeAuthenticationToken(Object phoneNumber, Object credentials) {
        super(null);
        this.principal = phoneNumber;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    //验证成功后通过该构造方法创建对象
    public SmsCodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal, Object credentials) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }
}
