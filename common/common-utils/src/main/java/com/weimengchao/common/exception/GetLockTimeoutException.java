package com.weimengchao.common.exception;

/**
 * 获取锁超时
 */
public class GetLockTimeoutException extends Exception {

    public GetLockTimeoutException(String message) {
        super(message);
    }

}
