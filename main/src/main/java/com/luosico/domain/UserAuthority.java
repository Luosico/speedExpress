package com.luosico.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/27
 */
public class UserAuthority extends User implements UserDetails {

    public UserAuthority() {

    }

    public UserAuthority(User user) {
        setUsername(user.getUsername());
        setPassword(user.getPassword());
        setAccountNonExpired(user.getAccountNonExpired());
        setAccountNonLocked(user.getAccountNonLocked());
        setCredentialsNonExpired(user.getCredentialsNonExpired());
        setEnabled(user.getEnabled());
        setAuthority(user.getAuthority());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(getAuthority()));
        return list;
    }

    @Override
    public boolean isAccountNonExpired() {
        return getAccountNonExpired() == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return getAccountNonLocked() == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getCredentialsNonExpired() == 1;
    }

    @Override
    public boolean isEnabled() {
        return getEnabled() == 1;
    }
}
