package com.luosico;

import com.luosico.domain.User;
import com.luosico.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/26
 */
@SpringBootTest
public class UserTest {
    @Autowired
    UserService userService;

    @Test
    public void test(){
        User user = userService.selectUserByUsername("user");
        System.out.println(user.getAuthority());
    }
}
