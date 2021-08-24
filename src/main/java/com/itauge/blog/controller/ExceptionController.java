package com.itauge.blog.controller;

import com.itauge.blog.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e){
        log.error("Request URL:{},Exception:{}",request.getRequestURL(),e.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url",request.getRequestURL());
        modelAndView.addObject("exception",e);
        if (e.getClass() == NotFoundException.class){
            modelAndView.setViewName("error/404");
        }else {
            modelAndView.setViewName("error/error");
        }
        return modelAndView;
    }
}
