package com.luosico;


import com.luosico.domain.User;
import com.luosico.domain.UserAuthority;
import com.luosico.user.UserUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/11
 */

@SpringBootTest
public class DemoTest {
    @Test
    public void test(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String str = "123456";
        String s = encoder.encode(str);
        System.out.println(s);
        System.out.println(encoder.matches(str,"$2a$10$7ebE8NO4xJo/EyOhpXJJmOEpMOdOkqreVelkaa04QTz/ByI/iB9km"));
    }

    @DubboReference
    UserUtil userUtil;

    @Test
    public void dubboTest(){
        User user = userUtil.selectUserByUsername("user");
        System.out.println(user.getAuthority());
    }

    @Test
    public void testUser(){
        User user = new User("name","pwd","123456");
        UserAuthority authority = new UserAuthority(user);
        System.out.println(authority.getAuthorities());
        System.out.println(authority.getPassword());
        System.out.println(authority.getUsername());
        System.out.println(authority.isAccountNonExpired());
        System.out.println(authority.isAccountNonLocked());
        System.out.println(authority.isCredentialsNonExpired());
        System.out.println(authority.isEnabled());
    }
}
