package com.weimengchao.common.constant;

import lombok.Getter;

@Getter
public enum OrderStatus {

    /**
     * 未知状态
     */
    UNKNOWN(0, "未知", "未知状态"),

    /**
     * 在线支付订单待支付；货到付款订单待确认
     */
    TO_BE_PAID(1, "待支付/待确认", "在线支付订单待支付；货到付款订单待确认"),

    /**
     * 备货中（只有此状态下，才可发货）
     */
    IN_STOCKING(2, "备货中", "备货中（只有此状态下，才可发货）"),

    /**
     * 已发货
     */
    SHIPPED(3, "已发货", "已发货"),

    /**
     * 已取消
     */
    CANCELLED(4, "已取消", "已取消：1.用户未支付时取消订单；2.用户超时未支付，系统自动取消订单；3.货到付款订单，用户拒收"),

    /**
     * 已完成
     */
    COMPLETED(5, "已完成", "已完成：1.在线支付订单，商家发货后，用户收货、拒收或者15天无物流更新；2.货到付款订单，用户确认收货"),
    ;

    private Integer status;
    private String title;
    private String desc;

    OrderStatus(Integer status, String title, String desc) {
        this.status = status;
        this.title = title;
        this.desc = desc;
    }

    /**
     * 转枚举
     *
     * @param status 状态值
     * @return 枚举
     */
    public static OrderStatus toEnum(Integer status) {
        if (status == null) {
            return UNKNOWN;
        }
        for (OrderStatus orderStatus : values()) {
            if (orderStatus.status.equals(status)) {
                return orderStatus;
            }
        }
        return UNKNOWN;
    }

}
