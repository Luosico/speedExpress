package com.luosico.service;

import com.luosico.sms.Sms;
import com.luosico.util.RedisUtils;
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

    @DubboReference
    RedisUtils redisUtil;

    /**
     * 发送短信验证码
     * @param phoneNumber 手机号码
     */
    public void sendSmsCode(String phoneNumber){
        smsUtil.sendSmsCode(phoneNumber);
    }

    /**
     * 发送指定长度短信验证码
     * @param phoneNumber 接收人手机号码
     * @param length 验证码长度
     */
    public void sendSmsCode(String phoneNumber, int length){
        smsUtil.sendSmsCode(phoneNumber,length);
    }

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

    /**
     * 验证号码是否发送了验证码
     * @param phoneNumber 手机号码
     * @return true存在，false不存在
     */
    public boolean isExit(String phoneNumber){
        return redisUtil.hasKey(phoneNumber);
    }

    /**
     * 校验验证码正确性
     * @param phoneNumber 手机号码
     * @param smsCode 验证码
     * @return true正确，false错误
     */
    public boolean isCorrect(String phoneNumber, String smsCode){
        //从redis中获取验证码
        String redisSmsCode = (String) redisUtil.get(phoneNumber);
        return redisSmsCode.equals(smsCode);
    }
}
