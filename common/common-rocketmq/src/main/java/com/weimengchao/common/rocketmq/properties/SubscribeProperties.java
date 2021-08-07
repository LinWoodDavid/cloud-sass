package com.weimengchao.common.rocketmq.properties;

import lombok.Data;

@Data
public class SubscribeProperties {

    private String topic;
    private String expression;

}
