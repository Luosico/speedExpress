package com.luosico;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/27
 */

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void setValue() {
        boolean result = redisUtil.set("v1", "1");
        System.out.println(result);
    }

    @Test
    public void getValue() {
        String result = (String)redisUtil.get("v1");
        System.out.println(result);
    }
}
