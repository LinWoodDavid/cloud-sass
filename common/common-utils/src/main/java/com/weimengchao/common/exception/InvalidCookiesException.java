package com.weimengchao.common.exception;

/**
 * cookies 无效
 */
public class InvalidCookiesException extends PlatformCallException {

    public InvalidCookiesException(String message) {
        super(message);
    }
}
