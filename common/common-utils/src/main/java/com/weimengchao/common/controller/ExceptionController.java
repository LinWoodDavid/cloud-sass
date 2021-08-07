package com.weimengchao.common.controller;

import com.weimengchao.common.constant.ResponseCode;
import com.weimengchao.common.http.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.Executable;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult<?> param(MethodArgumentNotValidException exception) {
        Executable executable = exception.getParameter().getExecutable();
        String methodName = executable.getDeclaringClass().getName() + "." + executable.getName();
        Object target = exception.getBindingResult().getTarget();
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        StringBuilder simpleMessages = new StringBuilder();
        for (ObjectError error : errors) {
            simpleMessages.append(error.getDefaultMessage()).append(";");
        }
        RestResult result = new RestResult<>(ResponseCode.BAD_REQUEST);
        log.error("响应: Return={}, ClassMethod={}, Parameters={}", result, methodName, target);
        return result;
    }

    @ExceptionHandler({BindException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult<?> param(BindException exception) {
        String methodName = exception.getObjectName();
        Object target = exception.getBindingResult().getTarget();
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        StringBuilder simpleMessages = new StringBuilder();
        for (ObjectError error : errors) {
            simpleMessages.append(error.getDefaultMessage()).append(";");
        }
        RestResult result = new RestResult<>(ResponseCode.BAD_REQUEST);
        log.error("响应: Return={}, ClassMethod={}, Parameters={}", result, methodName, target);
        return result;
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult<?> param(Exception exception) {
        log.error("Exception:{}", exception);
        return new RestResult<>(ResponseCode.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult<?> error(Exception exception) {
        log.error("全局Exception:{}", exception);
        return new RestResult<>(ResponseCode.SERVICE_EXCEPTION);
    }

}
