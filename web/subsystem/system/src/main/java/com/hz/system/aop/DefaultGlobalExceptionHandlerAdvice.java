package com.hz.system.aop;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * 异常拦截处理
 */
@ControllerAdvice(value = "com.hz.system.controller")
@Slf4j
public class DefaultGlobalExceptionHandlerAdvice {

    /**
     * 基础异常.
     *
     * @param ex
     * @return CommonResult
     */
    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseBody
    public String exception(RuntimeException ex) {
        ex.printStackTrace();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();
        response.setStatus(500);
        return ex.getMessage();
    }

    /**
     * 基础异常
     *
     * @param ex 7
     * @return CommonResult
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public String exception(Exception ex) {
        ex.printStackTrace();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();
        response.setStatus(500);
        return ex.getMessage();
    }
}
