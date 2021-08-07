package com.weimengchao.common.constant;

//支付类型 (0：货到付款，1：微信，2：支付宝）
public enum PayType {

    unknown(-1, "未知"),
    CashOnDelivery(0, "货到付款"),
    WeChat(1, "微信"),
    AliPay(2, "支付宝"),
    ;

    private final Integer type;
    private final String title;

    PayType(Integer type, String title) {
        this.type = type;
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public static PayType valueOf(Integer type) {
        if (type == null) {
            return unknown;
        }
        switch (type) {
            case 0:
                return CashOnDelivery;
            case 1:
                return WeChat;
            case 2:
                return AliPay;
            default:
                return unknown;
        }
    }

}
