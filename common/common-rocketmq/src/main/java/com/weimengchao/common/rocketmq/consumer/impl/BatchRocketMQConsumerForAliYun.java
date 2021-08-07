package com.weimengchao.common.rocketmq.consumer.impl;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.batch.BatchMessageListener;
import com.aliyun.openservices.ons.api.bean.BatchConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.weimengchao.common.rocketmq.consumer.BatchRocketMQConsumer;
import com.weimengchao.common.rocketmq.domain.RocketMQMessage;
import com.weimengchao.common.rocketmq.listener.BatchRocketMQMessageListener;
import com.weimengchao.common.rocketmq.properties.ConsumerProperties;
import com.weimengchao.common.rocketmq.properties.RocketMQProperties;
import com.weimengchao.common.rocketmq.properties.SubscribeProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class BatchRocketMQConsumerForAliYun implements BatchRocketMQConsumer {

    private BatchConsumerBean batchConsumerBean;

    public BatchRocketMQConsumerForAliYun(ConsumerProperties consumerProperties,
                                          RocketMQProperties rocketMQProperties, BatchRocketMQMessageListener listener) {

        this.batchConsumerBean = new BatchConsumerBean();
        //配置文件
        Properties properties = consumerProperties.getConsumerProperties(rocketMQProperties);
        properties.setProperty(PropertyKeyConst.ConsumeMessageBatchMaxSize, consumerProperties.getConsumeMessageBatchMaxSize());
        batchConsumerBean.setProperties(properties);

        //创建BatchMessageListener
        BatchMessageListener batchMessageListener = new BatchMessageListener() {
            @Override
            public Action consume(List<Message> messages, ConsumeContext context) {
                return processingMessages(messages, listener);
            }
        };
        //订阅关系
        Map<Subscription, BatchMessageListener> subscriptionTable = new HashMap<Subscription, BatchMessageListener>();
        for (SubscribeProperties subscribe : consumerProperties.getSubscribes()) {
            Subscription subscription = new Subscription();
            subscription.setTopic(subscribe.getTopic());
            subscription.setExpression(subscribe.getExpression());
            subscriptionTable.put(subscription, batchMessageListener);
        }
        this.batchConsumerBean.setSubscriptionTable(subscriptionTable);
    }

    /**
     * 消息处理
     *
     * @param messages messages
     * @param listener listener
     * @return Action
     */
    private Action processingMessages(List<Message> messages, BatchRocketMQMessageListener listener) {
        //消息反序列化
        List<RocketMQMessage> messageList = new ArrayList<>();
        try {
            for (Message message : messages) {
                String msg = new String(message.getBody(), "utf-8");
                RocketMQMessage rocketMQMessage = RocketMQMessage.builder()
                        .tag(message.getTag())
                        .key(message.getKey())
                        .message(msg)
                        .msgId(message.getMsgID())
                        .reconsumeTimes(message.getReconsumeTimes())
                        .topic(message.getTopic())
                        .build();

                messageList.add(rocketMQMessage);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("消息编码异常不再接收此条消息!请检查生产者消息序列化方式。messageList={},listener:{},e:"
                    , JSON.toJSONString(messageList), listener.getClass().toString(), e);
            return Action.CommitMessage;
        }

        //消费消息
        try {
            boolean consume = listener.consume(messageList);
            if (consume) {
                return Action.CommitMessage;
            } else {
                return Action.ReconsumeLater;
            }
        } catch (Exception e) {
            log.error("消费异常请检查消费者代码!messageList={},listener:{},e:"
                    , JSON.toJSON(messageList), listener.getClass().toString(), e);
            return Action.ReconsumeLater;
        }
    }

    @Override
    public void start() {
        this.batchConsumerBean.start();
    }

    @Override
    public void shutdown() {
        if (this.batchConsumerBean != null) {
            this.batchConsumerBean.shutdown();
        }
    }
}
