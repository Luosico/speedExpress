package com.luosico.sms;

/**
 * 短信验证码相关服务
 *
 * @Author: luo kai fa
 * @Date: 2021/1/19
 */
public interface Sms {

    /**
     * 向指定手机号码发送短信验证码，默认为6位
     *
     * @param phoneNumber 短信接收人手机号码
     */
    public void sendSmsCode(String phoneNumber);

    /**
     * 向指定手机号码发送短信验证码
     * @param phoneNumber 短信接收人手机号码
     * @param length 验证码长度
     */
    public void sendSmsCode(String phoneNumber,int length);

    /**
     * 向指定手机号码发送短信消息
     *
     * @param phoneNumber 短信接收人手机号码
     * @param message 短信内容
     */
    public void sendSmsMessage(String phoneNumber, String message);

    /**
     * 获取6位数字短信验证码
     *
     * @return 短信验证码，6位数字
     */
    default public String getSmsCode(){
        return getSmsCode(6);
    }

    /**
     * 获取指定长度的数字验证码
     * @param length 验证码长度
     * @return 指定长度的数字验证码
     */
    public String getSmsCode(int length);
}
