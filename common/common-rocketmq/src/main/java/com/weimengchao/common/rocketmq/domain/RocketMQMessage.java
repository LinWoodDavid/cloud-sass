package com.weimengchao.common.rocketmq.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Builder
public class RocketMQMessage {

    private String message;
    private String msgId;
    private String key;
    private String tag;
    private String topic;
    //重新消费次数
    private int reconsumeTimes;
}
