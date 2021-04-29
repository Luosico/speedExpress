package com.luosico;

import com.luosico.config.PayStatus;
import com.luosico.domain.Pay;
import com.luosico.mapper.PayMapper;
import com.luosico.service.MapperService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: luo kai fa
 * @Date: 2021/4/29
 */
@SpringBootTest
public class PayTest {
    @Autowired
    MapperService service;

    @Test
    public void testEnum(){
        service.updatePayStatus(10);
    }
}
