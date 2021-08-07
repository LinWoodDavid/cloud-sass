package com.weimengchao.common.rocketmq.constant;

/**
 * 消费者组
 */
public enum ConsumerGroup {

    EVALUATE_JSON_SYNC("GID_evaluate_sync", "评价json同步"),
    ORDER_JSON_SYNC("GID_order_sync", "订单json同步"),
    TASK_EXPORT("GID_task_export", "导出Excel"),
    ;

    private final String groupId;
    private final String desc;

    ConsumerGroup(String groupId, String desc) {
        this.groupId = groupId;
        this.desc = desc;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getDesc() {
        return desc;
    }

}
