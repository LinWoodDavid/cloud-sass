package com.weimengchao.common.constant;

import lombok.Getter;

/**
 * 订单项状态
 *
 * @author Administrator
 * @date 2021年05月24日22:51
 * @description
 */
@Getter
public enum OrderItemStatus {
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
    CANCELLED(4, "已取消", "已取消：1.用户未支付时取消订单；2.用户超时未支付，系统自动取消订单；3.货到付款订单，用户拒收"),
    COMPLETED(5, "已完成", "已完成：1.在线支付订单，商家发货后，用户收货、拒收或者15天无物流更新；2.货到付款订单，用户确认收货"),
    RETURNING1(6, "退货中-用户申请", "退货中-用户申请"),
    RETURNING2(7, "退货中-商家同意退货", "退货中-商家同意退货"),
    RETURNING3(8, "退货中-客服仲裁", "退货中-客服仲裁"),
    RETURN_CLOSED(9, "已关闭-退货失败", "已关闭-退货失败"),
    RETURNING4(10, "退货中-客服同意", "退货中-客服同意"),
    RETURNING5(11, "退货中-用户已填物流", "退货中-用户已填物流"),
    RETURN_SUCCESSFUL1(12, "退货成功-商户同意", "退货成功-商户同意"),
    RETURNING6(13, "退货中-再次客服仲裁", "退货中-再次客服仲裁"),
    RETURNING7(14, "退货中-客服同意退款", "退货中-客服同意退款"),
    RETURNING8(15, "退货中-用户取消", "退货-用户取消"),
    REFUND1(16, "退款中-用户申请(备货中)", "退款中-用户申请(备货中)"),
    REFUND2(17, "退款中-商家同意(备货中)", "退款中-商家同意(备货中)"),
    REFUND_SUCCESSFUL1(21, "退款成功-订单退款-备货中", "退款成功-订单退款（备货中，用户申请退款，最终退款成功）"),
    REFUND_SUCCESSFUL2(22, "退款成功-订单退款-已发货", "退款成功-订单退款 (已发货时，用户申请退货，最终退货退款成功)"),
    RETURN_SUCCESSFUL2(24, "退货成功-商户已退款", "退货成功-商户已退款 (特指货到付款订单，已发货时，用户申请退货，最终退货退款成功)"),
    REFUND3(25, "退款中-用户取消", "退款中-用户取消(备货中)"),
    REFUND4(26, "退款中-商家拒绝", "退款中-商家拒绝（备货中）"),
    RETURNING9(27, "退货中-等待买家处理", "退货中-等待买家处理（已发货，商家拒绝用户退货申请）"),
    RETURN_FAILED(28, "退货失败", "退货失败（已发货，商家拒绝用户退货申请，客服仲裁支持商家）"),
    RETURNING10(29, "退货中-等待买家处理", "退货中-等待买家处理（已发货，用户填写退货物流，商家拒绝）"),
    REFUND5(30, "退款中-退款申请", "退款中-退款申请（已发货，用户申请仅退款）"),
    REFUND6(31, "退款申请取消", "退款申请取消（已发货，用户申请仅退款时，取消申请）"),
    REFUND7(32, "退款中-商家同意", "退款中-商家同意（已发货，用户申请仅退款，商家同意申请）"),
    REFUND8(33, "退款中-商家拒绝", "退款中-商家拒绝（已发货，用户申请仅退款，商家拒绝申请）"),
    REFUND9(34, "退款中-客服仲裁", "退款中-客服仲裁（已发货，用户申请仅退款，商家拒绝申请，买家申请客服仲裁）"),
    REFUND10(35, "退款中-客服同意", "退款中-客服同意（已发货，用户申请仅退款，商家拒绝申请，客服仲裁支持买家）"),
    REFUND11(36, "退款中-支持商家", "退款中-支持商家（已发货，用户申请仅退款，商家拒绝申请，客服仲裁支持商家）"),
    REFUND_CLOSED2(37, "已关闭-退款失败", "已关闭-退款失败（已发货，用户申请仅退款时，退款关闭）"),
    RETURN_SUCCESSFUL3(38, "退款成功-售后退款", "退款成功-售后退款（特指货到付款订单，已发货，用户申请仅退款时，最终退款成功）"),
    RETURN_SUCCESSFUL4(39, "退款成功-订单退款", "退款成功-订单退款（已发货，用户申请仅退款时，最终退款成功）"),
    ;
    private Integer status;
    private String title;
    private String desc;

    OrderItemStatus(Integer status, String title, String desc) {
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
    public static OrderItemStatus toEnum(Integer status) {
        if (status == null) {
            return UNKNOWN;
        }
        for (OrderItemStatus orderItemStatus : values()) {
            if (orderItemStatus.status.equals(status)) {
                return orderItemStatus;
            }
        }
        return UNKNOWN;
    }

}
