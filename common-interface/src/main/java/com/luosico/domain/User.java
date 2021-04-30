package com.luosico.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/26
 */
public class User implements Serializable {

    private long id;
    private String username;
    private String name;
    private String password;
    private String phoneNumber;
    private Timestamp createTime;
    private String authority;
    private byte accountNonExpired;
    private byte accountNonLocked;
    private byte credentialsNonExpired; //凭证是否过期
    private byte enabled;


    transient PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User() {

    }

    public User(String password, String phoneNumber) {
        this.password = passwordEncoder.encode(password);
        this.phoneNumber = phoneNumber;
    }

    public User(String username, String password, String phoneNumber) {
        this.username = username;
        //密码加密
        this.password = passwordEncoder.encode(password);
        this.phoneNumber = phoneNumber;
        this.createTime = Timestamp.valueOf(LocalDateTime.now());
        this.authority = "ROLE_USER";
        this.accountNonExpired = 1;
        this.accountNonLocked = 1;
        this.credentialsNonExpired = 1;
        this.enabled = 1;

    }

    /**
     * 密码加密后在设置
     */
    public void setEncodePassword(String password) {
        this.password = passwordEncoder.encode(password);
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public byte getEnabled() {
        return enabled;
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

    public void setAccountNonExpired(byte accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public byte getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonLocked(byte accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public byte getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setCredentialsNonExpired(byte credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public byte getCredentialsNonExpired() {
        return credentialsNonExpired;
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

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
