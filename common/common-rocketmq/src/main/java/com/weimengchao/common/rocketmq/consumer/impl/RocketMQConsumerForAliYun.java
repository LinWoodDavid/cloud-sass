package com.weimengchao.common.rocketmq.consumer.impl;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.weimengchao.common.rocketmq.consumer.RocketMQConsumer;
import com.weimengchao.common.rocketmq.domain.RocketMQMessage;
import com.weimengchao.common.rocketmq.listener.RocketMQMessageListener;
import com.weimengchao.common.rocketmq.properties.ConsumerProperties;
import com.weimengchao.common.rocketmq.properties.RocketMQProperties;
import com.weimengchao.common.rocketmq.properties.SubscribeProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RocketMQConsumerForAliYun implements RocketMQConsumer {

    private ConsumerBean consumerBean;

    public RocketMQConsumerForAliYun(ConsumerProperties consumerProperties, RocketMQProperties rocketMQProperties, RocketMQMessageListener listener) {
        this.consumerBean = new ConsumerBean();
        this.consumerBean.setProperties(consumerProperties.getConsumerProperties(rocketMQProperties));
        //创建MessageListener
        MessageListener messageListener = new MessageListener() {
            @Override
            public Action consume(Message message, ConsumeContext context) {
                return processingMessages(message, listener);
            }
        };
        //订阅关系
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<>();
        for (SubscribeProperties subscribe : consumerProperties.getSubscribes()) {
            Subscription subscription = new Subscription();
            subscription.setTopic(subscribe.getTopic());
            subscription.setExpression(subscribe.getExpression());
            subscriptionTable.put(subscription, messageListener);
        }
        this.consumerBean.setSubscriptionTable(subscriptionTable);
    }

    /**
     * 处理消息
     *
     * @param message  message
     * @param listener listener
     * @return Action
     */
    private Action processingMessages(Message message, RocketMQMessageListener listener) {
        //反序列化
        RocketMQMessage rocketMQMessage = null;
        try {
            String msg = new String(message.getBody(), "utf-8");
            rocketMQMessage = RocketMQMessage.builder()
                    .tag(message.getTag())
                    .key(message.getKey())
                    .message(msg)
                    .msgId(message.getMsgID())
                    .topic(message.getTopic())
                    .reconsumeTimes(message.getReconsumeTimes())
                    .build();
        } catch (UnsupportedEncodingException e) {
            log.error("消息编码异常不再接收此条消息!请检查生产者消息序列化方式。topic={},listener:{},e:"
                    , message.getTopic(), listener.getClass().toString(), e);
            return Action.CommitMessage;
        }

        //业务处理
        try {
            boolean consume = listener.consume(rocketMQMessage);
            if (consume) {
                return Action.CommitMessage;
            } else {
                return Action.ReconsumeLater;
            }
        } catch (Exception e) {
            log.error("消费异常请检查消费者代码!topic={},rocketMQMessage={},listener:{},e:"
                    , message.getTopic(), JSON.toJSON(rocketMQMessage), listener.getClass().toString(), e);
            return Action.ReconsumeLater;
        }
    }

    @Override
    public void start() {
        if (this.consumerBean != null) {
            this.consumerBean.start();
        }
    }

    @Override
    public void shutdown() {
        if (this.consumerBean != null) {
            this.consumerBean.shutdown();
        }
    }

}
