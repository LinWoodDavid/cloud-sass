package com.weimengchao.common.constant;

import lombok.Getter;

@Getter
public enum TaskStatus {

//    PENDING(0, "待处理"),
    PROCESS(1, "处理中"),
    COMPLETED(2, "已完成"),
    FAIL(3, "失败"),
    ;

    private final int code;
    private final String desc;

    TaskStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
