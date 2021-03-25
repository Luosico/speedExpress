package com.luosico;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/11
 */


public class DemoTest {
    @Test
    public void test(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String str = "123456";
        String s = encoder.encode(str);
        System.out.println(s);
        System.out.println(encoder.matches(str,"$2a$10$7ebE8NO4xJo/EyOhpXJJmOEpMOdOkqreVelkaa04QTz/ByI/iB9km"));
    }
}
