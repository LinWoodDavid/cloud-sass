package com.weimengchao.common.constant;

import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCEED(200, null),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "Not Found"),
    SERVICE_EXCEPTION(500, "服务器异常"),
    FAILED(600, "请求失败"),
    TIME_OUT(1000, "响应超时请稍后再试"),
    INVALID_REQUEST_PARAMETER(10001, "请求参数无效"),
    ;

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
