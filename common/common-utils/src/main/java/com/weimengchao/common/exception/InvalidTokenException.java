package com.weimengchao.common.exception;

/**
 * token 无效
 */
public class InvalidTokenException extends PlatformCallException {

    public InvalidTokenException(String message) {
        super(message);
    }
}
