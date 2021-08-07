package com.weimengchao.common.constant;

import lombok.Getter;

/**
 * 评价处理状态 0:未处理，1:处理中，2:已处理
 */
@Getter
public enum ProcessState {

    UNTREATED(0, "未处理"),
    PROCESSING(1, "处理中"),
    PROCESSED(2, "已处理"),
    ;

    private final Integer value;
    private final String title;

    ProcessState(Integer value, String title) {
        this.value = value;
        this.title = title;
    }

}
