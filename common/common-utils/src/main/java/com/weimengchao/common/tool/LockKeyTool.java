package com.weimengchao.common.tool;

/**
 * 锁key值拼接
 */
public class LockKeyTool {

    /**
     * 店铺请求频率控制key
     *
     * @param extShopId
     * @return
     */
    public static String shopAwaitKey(String extShopId) {
        return new StringBuilder()
                .append("await-shop")
                .append("-").append(extShopId)
                .toString();
    }

    /**
     * 订单锁key值
     *
     * @param extOrderId 商城单号
     * @return
     */
    public static String orderLockKey(String extOrderId) {
        return new StringBuilder()
                .append("lock-order")
                .append("-").append(extOrderId)
                .toString();
    }

    /**
     * 评价锁key值
     *
     * @param extEvaluateId 商城评价id
     * @return
     */
    public static String evaluateLockKey(String extEvaluateId) {
        return new StringBuilder()
                .append("lock-evaluate")
                .append("-").append(extEvaluateId)
                .toString();
    }

    /**
     * 店铺最后抓取时间
     *
     * @param shopId
     * @return
     */
    public static String lastGrabOrderTimeKey(Long shopId) {
        return new StringBuilder()
                .append("shop-grab-order-last-time-").append(shopId)
                .toString();
    }

}
