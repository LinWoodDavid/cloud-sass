package com.weimengchao.common.rocketmq.producer;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQClientException;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.weimengchao.common.rocketmq.domain.RocketMQSendResult;
import com.weimengchao.common.rocketmq.exception.MQProducerException;

import java.io.UnsupportedEncodingException;

public interface RocketMQProducer {

    void start() throws MQClientException;

    RocketMQSendResult syncSend(String topic, String tags, String body, String key) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException, MQProducerException;

    void shutdown();

}
