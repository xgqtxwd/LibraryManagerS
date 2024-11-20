package com.xu.exception;

import com.xu.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
    全局异常处理*/
@RestControllerAdvice//异常处理类
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)//捕获异常
    public Result exception(Exception e) {
        e.printStackTrace();
        return Result.error("请求异常，请联系管理员");
    }
}
