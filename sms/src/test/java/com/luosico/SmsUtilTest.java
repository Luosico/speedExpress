package com.luosico;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/21
 */

@SpringBootTest
class SmsUtilTest {

    @Autowired
    SmsUtil smsUtil;

    @Test
    void getSmsCode() {
        System.out.println(smsUtil.getSmsCode());
    }

    @ParameterizedTest
    @ValueSource(ints = {7,7,7,8,8,8})
    void testGetSmsCode(int length) {
        System.out.println(smsUtil.getSmsCode(length));
    }
}