package com.luosico.exception;



/**
 * @Author: luo kai fa
 * @Date: 2021/1/13
 */
public class RestDemoException extends RuntimeException{
    String data;

    public RestDemoException(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

}
