package com.hz.system.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @explain 全局异常处理
 * @Classname ExceptionHandler
 * @Date 2021/9/22 16:30
 * @Created by hanzhao
 */
@ControllerAdvice
public class SpringExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView customException(Exception e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", e.getMessage());
        mv.setViewName("myerror");
        return mv;
    }
}
