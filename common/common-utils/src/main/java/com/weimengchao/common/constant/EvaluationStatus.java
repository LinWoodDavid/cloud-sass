package com.weimengchao.common.constant;

public enum EvaluationStatus {

    NOT_EVALUATED(0, "未评价"),
    EVALUATED(1, "已评价"),
    ;

    private final Integer value;
    private final String title;

    EvaluationStatus(Integer value, String title) {
        this.value = value;
        this.title = title;
    }

    public Integer getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

    public static EvaluationStatus valueOf(Integer value) {
        switch (value) {
            case 0:
                return NOT_EVALUATED;
            case 1:
                return EVALUATED;
            default:
                throw new RuntimeException("评价状态不存在value:" + value);
        }
    }
}
