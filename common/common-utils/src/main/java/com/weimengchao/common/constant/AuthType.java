package com.weimengchao.common.constant;

/**
 * 店铺授权类型(0:工具应用,1:自研应用)
 */
public enum AuthType {

    PLATFORM(0, "工具应用"),
    SELF(1, "自研应用"),
    ;

    private final Integer value;
    private final String title;

    AuthType(Integer value, String title) {
        this.value = value;
        this.title = title;
    }

    public Integer getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

}
