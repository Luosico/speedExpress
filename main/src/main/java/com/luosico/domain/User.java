package com.luosico.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/13
 */
public class User implements UserDetails {

    private long id;
    private String username;
    private String password;
    private String phoneNumber;
    private Timestamp createTime;
    private Collection<? extends GrantedAuthority> authorities;
    private byte accountNonExpired;
    private byte accountNonLocked;
    private byte credentialsNonExpired; //凭证是否过期
    private byte enabled;

    public User(){

    }

    public User(String password, String phoneNumber) {
        this.password = new BCryptPasswordEncoder().encode(password);
        this.phoneNumber = phoneNumber;
    }

    public User(String username, String password, String phoneNumber) {
        this.username = username;
        //密码加密
        this.password = new BCryptPasswordEncoder().encode(password);
        this.phoneNumber = phoneNumber;
        this.createTime = Timestamp.valueOf(LocalDateTime.now());
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //用户身份
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        this.authorities = authorities;
        this.accountNonExpired = 1;
        this.accountNonLocked = 1;
        this.credentialsNonExpired = 1;
        this.enabled = 1;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired != 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked != 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired != 0;
    }

    @Override
    public boolean isEnabled() {
        return enabled != 0;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAccountNonExpired(byte accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(byte accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(byte credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(byte enabled) {
        this.enabled = enabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
