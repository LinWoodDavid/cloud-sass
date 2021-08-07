package com.weimengchao.common.rocketmq;

import com.weimengchao.common.rocketmq.consumer.BatchRocketMQConsumer;
import com.weimengchao.common.rocketmq.consumer.RocketMQConsumer;
import com.weimengchao.common.rocketmq.consumer.impl.BatchRocketMQConsumerForAliYun;
import com.weimengchao.common.rocketmq.consumer.impl.BatchRocketMQConsumerImpl;
import com.weimengchao.common.rocketmq.consumer.impl.RocketMQConsumerForAliYun;
import com.weimengchao.common.rocketmq.consumer.impl.RocketMQConsumerImpl;
import com.weimengchao.common.rocketmq.listener.BatchRocketMQMessageListener;
import com.weimengchao.common.rocketmq.listener.RocketMQMessageListener;
import com.weimengchao.common.rocketmq.producer.RocketMQProducer;
import com.weimengchao.common.rocketmq.producer.impl.RocketMQProducerForAliYun;
import com.weimengchao.common.rocketmq.producer.impl.RocketMQProducerImpl;
import com.weimengchao.common.rocketmq.properties.ConsumerProperties;
import com.weimengchao.common.rocketmq.properties.ProducerProperties;
import com.weimengchao.common.rocketmq.properties.RocketMQProperties;

public class RocketMQBuilder {

    public static RocketMQProducer builder(ProducerProperties producerProperties, RocketMQProperties rocketMQProperties) {

        if (rocketMQProperties.isAliYun()) {
            return new RocketMQProducerForAliYun(producerProperties, rocketMQProperties);
        } else {
            return new RocketMQProducerImpl(producerProperties, rocketMQProperties);
        }
    }

    public static RocketMQConsumer builder(ConsumerProperties consumerProperties, RocketMQProperties rocketMQProperties, RocketMQMessageListener listener) {
        if (rocketMQProperties.isAliYun()) {
            return new RocketMQConsumerForAliYun(consumerProperties, rocketMQProperties, listener);
        } else {
            return new RocketMQConsumerImpl(consumerProperties, rocketMQProperties, listener);
        }
    }

    public static BatchRocketMQConsumer builder(ConsumerProperties consumerProperties, RocketMQProperties rocketMQProperties, BatchRocketMQMessageListener listener) {
        if (rocketMQProperties.isAliYun()) {
            return new BatchRocketMQConsumerForAliYun(consumerProperties, rocketMQProperties, listener);
        } else {
            return new BatchRocketMQConsumerImpl(consumerProperties, rocketMQProperties, listener);
        }
    }

}
