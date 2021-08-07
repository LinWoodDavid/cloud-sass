package com.weimengchao.common.rocketmq.producer.impl;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQClientException;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.weimengchao.common.rocketmq.domain.RocketMQSendResult;
import com.weimengchao.common.rocketmq.producer.RocketMQProducer;
import com.weimengchao.common.rocketmq.properties.ProducerProperties;
import com.weimengchao.common.rocketmq.properties.RocketMQProperties;

import java.io.UnsupportedEncodingException;

public class RocketMQProducerForAliYun implements RocketMQProducer {

    private ProducerBean producerBean;

    public RocketMQProducerForAliYun(ProducerProperties producerProperties, RocketMQProperties rocketMQProperties) {
        ProducerBean producerBean = new ProducerBean();
        producerBean.setProperties(producerProperties.getProducerProperties(rocketMQProperties));
        this.producerBean = producerBean;
    }

    @Override
    public void start() {
        if (this.producerBean != null) {
            this.producerBean.start();
        }
    }

    @Override
    public RocketMQSendResult syncSend(String topic, String tags, String body, String key) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message(topic, tags, key, body.getBytes());
        SendResult sendResult = this.producerBean.send(message);
        return RocketMQSendResult.builder()
                .messageId(sendResult.getMessageId())
                .msgId(sendResult.getMessageId())
                .topic(sendResult.getTopic())
                .build();
    }

    @Override
    public void shutdown() {
        if (this.producerBean != null) {
            this.producerBean.shutdown();
        }
    }

}
