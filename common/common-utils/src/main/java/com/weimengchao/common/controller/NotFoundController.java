package com.weimengchao.common.controller;

import com.weimengchao.common.constant.ResponseCode;
import com.weimengchao.common.http.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@RestController
public class NotFoundController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = {ERROR_PATH})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestResult<?> notFound(HttpServletRequest request) {
        return new RestResult<>(ResponseCode.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
