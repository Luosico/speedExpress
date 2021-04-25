package com.luosico.domain;

import java.io.Serializable;

/**
 * 返回的 json 结构
 *
 * @Author: luo kai fa
 * @Date: 2021/4/25
 */
public class JsonStructure<T> implements Serializable {
    /**
     * 执行结果
     * ok/fail
     */
    String status;

    /**
     * 错误信息
     */
    String message;

    /**
     * 数据
     */
    T data;

    public JsonStructure(){
        status = "ok";
        message = "";
        data = null;
    }

    public JsonStructure(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public JsonStructure(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public JsonStructure(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
