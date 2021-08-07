package com.weimengchao.common.rocketmq.domain;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.producer.SendStatus;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.common.message.MessageQueue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Builder
public class RocketMQSendResult implements Serializable {

    //阿里云RocketMQ返回结果
    private String messageId;
    private String topic;

    //本地搭建RocketMQ返回结果
    private SendStatus sendStatus;
    private String msgId;
    private MessageQueue messageQueue;
    private long queueOffset;
    private String transactionId;
    private String offsetMsgId;
    private String regionId;
    private boolean traceOn = true;

}
