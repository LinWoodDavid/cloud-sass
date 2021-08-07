package com.weimengchao.common.rocketmq.producer.impl;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQClientException;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.producer.SendResult;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.producer.SendStatus;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.common.message.Message;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.weimengchao.common.rocketmq.domain.RocketMQSendResult;
import com.weimengchao.common.rocketmq.exception.MQProducerException;
import com.weimengchao.common.rocketmq.producer.RocketMQProducer;
import com.weimengchao.common.rocketmq.properties.ProducerProperties;
import com.weimengchao.common.rocketmq.properties.RocketMQProperties;

public class RocketMQProducerImpl implements RocketMQProducer {

    private DefaultMQProducer defaultMQProducer;

    public RocketMQProducerImpl(ProducerProperties producerProperties, RocketMQProperties rocketMQProperties) {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(producerProperties.getProducerGroup());
        defaultMQProducer.setNamesrvAddr(rocketMQProperties.getNameSrvAddr());
        defaultMQProducer.setInstanceName(rocketMQProperties.getInstanceName());
        this.defaultMQProducer = defaultMQProducer;
    }

    @Override
    public void start() throws MQClientException {
        if (defaultMQProducer != null) {
            defaultMQProducer.start();
        }
    }

    @Override
    public RocketMQSendResult syncSend(String topic, String tags, String body, String key) throws InterruptedException, RemotingException, MQClientException, MQBrokerException, MQProducerException {
        Message message = new Message(topic, tags, key, body.getBytes());
        SendResult sendResult = defaultMQProducer.send(message);
        if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    .append("RocketMQProducer发送消息异常,")
                    .append("topic:").append(topic)
                    .append("tags:").append(tags)
                    .append("body").append(body)
                    .append(key).append(key)
            ;
            throw new MQProducerException(stringBuilder.toString());
        }
        return toRocketMQSendResult(sendResult);
    }

    private RocketMQSendResult toRocketMQSendResult(SendResult sendResult) {
        return RocketMQSendResult.builder()
                .sendStatus(sendResult.getSendStatus())
                .msgId(sendResult.getMsgId())
                .messageId(sendResult.getMsgId())
                .queueOffset(sendResult.getQueueOffset())
                .transactionId(sendResult.getTransactionId())
                .offsetMsgId(sendResult.getOffsetMsgId())
                .regionId(sendResult.getRegionId())
                .traceOn(sendResult.isTraceOn())
                .build();
    }

    @Override
    public void shutdown() {
        if (defaultMQProducer != null) {
            defaultMQProducer.shutdown();
        }
    }

}
