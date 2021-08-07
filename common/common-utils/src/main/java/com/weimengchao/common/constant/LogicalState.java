package com.weimengchao.common.constant;

public enum LogicalState {

    INVALID(0, "无效"),
    VALID(1, "有效"),
    ;

    private final Integer value;
    private final String title;

    LogicalState(Integer value, String title) {
        this.value = value;
        this.title = title;
    }

    public Integer getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

    public static LogicalState valueOf(Integer value) {
        if (value == null) {
            return INVALID;
        }
        switch (value) {
            case 1:
                return VALID;
            default:
                return INVALID;
        }
    }

}
