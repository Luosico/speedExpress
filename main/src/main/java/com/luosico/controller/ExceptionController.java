package com.luosico.controller;


import com.luosico.exception.RestDemoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author: luo kai fa
 * @Date: 2021/1/13
 */

@Controller
public class ExceptionController {

    @ExceptionHandler(RestDemoException.class) //必须放在Controller方法中
    public ResponseEntity demoNotFound(RestDemoException exception){
        String data = exception.getData();

        String message = null;
        return new ResponseEntity(message, HttpStatus.NOT_FOUND);
    }
}
