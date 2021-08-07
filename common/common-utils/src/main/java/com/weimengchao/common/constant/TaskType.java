package com.weimengchao.common.constant;

import lombok.Getter;

@Getter
public enum TaskType {

    ORDER_EXPORT(0, "订单导出"),
    EVALUATE_EXPORT(1, "评价导出"),
    ;

    private final int type;
    private final String desc;

    TaskType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
