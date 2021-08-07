package com.weimengchao.common.rocketmq.constant;

public enum Topic {

    EVALUATE_JSON("evaluate_json", "评价原始数据"),
    ORDER_JSON("order_json", "订单原始数据"),
    TASK("task", "任务中心"),
    ;

    //生产者组
    private final static String producerGroup = "GID_producer";

    private final String name;
    private final String desc;

    Topic(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
