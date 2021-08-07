package com.weimengchao.common.tool;

public enum BusinessType {

    account("00", "账户"),
    product("01", "商品"),
    order("02", "订单"),
    ;
    private final String number;
    private final String title;

    BusinessType(String number, String title) {
        this.number = number;
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }
}
