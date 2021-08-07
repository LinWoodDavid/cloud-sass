package com.weimengchao.common.rocketmq.constant;

public enum Subscribes {

    //评价json同步
    EVALUATE_JSON_SYNC(ConsumerGroup.EVALUATE_JSON_SYNC, Topic.EVALUATE_JSON, "evaluate_json_sync"),
    ORDER_JSON_SYNC(ConsumerGroup.ORDER_JSON_SYNC, Topic.ORDER_JSON, "order_json_sync"),
    EXPORT(ConsumerGroup.TASK_EXPORT, Topic.TASK, "task_export"),
    ;

    private final ConsumerGroup consumerGroup;
    private final Topic topic;
    private final String expression;

    Subscribes(ConsumerGroup consumerGroup, Topic topic, String expression) {
        this.consumerGroup = consumerGroup;
        this.topic = topic;
        this.expression = expression;
    }

    public ConsumerGroup getConsumerGroup() {
        return consumerGroup;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getExpression() {
        return expression;
    }

}
