package com.luosico.service;

import com.luosico.sms.Sms;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/21
 *
 * 短信相关服务
 */
@Service
public class SmsService {

    @DubboReference
    Sms smsUtil;


    /**
     * 获取短信验证码，默认我6位
     */
    public String getSmsCode(){
        return smsUtil.getSmsCode();
    }

    /**
     * 获取指定长度短信验证码
     * @param length 验证码长度
     */
    public String getSmsCode(int length){
        return smsUtil.getSmsCode(length);
    }
}
