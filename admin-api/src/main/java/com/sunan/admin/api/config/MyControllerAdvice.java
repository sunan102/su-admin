package com.sunan.admin.api.config;


import com.sunan.admin.api.common.RetVal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyControllerAdvice {
    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public RetVal errorHandler(Exception ex) {
        ex.printStackTrace();
        return new RetVal(1, "服务器异常",ex.getMessage());
    }
}
