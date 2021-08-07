package com.weimengchao.common.rocketmq.consumer.impl;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQClientException;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.common.message.MessageExt;
import com.weimengchao.common.rocketmq.consumer.RocketMQConsumer;
import com.weimengchao.common.rocketmq.domain.RocketMQMessage;
import com.weimengchao.common.rocketmq.listener.RocketMQMessageListener;
import com.weimengchao.common.rocketmq.properties.ConsumerProperties;
import com.weimengchao.common.rocketmq.properties.RocketMQProperties;
import com.weimengchao.common.rocketmq.properties.SubscribeProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
public class RocketMQConsumerImpl implements RocketMQConsumer {

    private DefaultMQPushConsumer consumer;

    public RocketMQConsumerImpl(ConsumerProperties consumerProperties,
                                RocketMQProperties rocketMQProperties,
                                RocketMQMessageListener listener) {
        // 实例化消费者
        this.consumer = new DefaultMQPushConsumer(consumerProperties.getGroupId());
        // 设置NameServer的地址
        this.consumer.setNamesrvAddr(rocketMQProperties.getNameSrvAddr());
        this.consumer.setInstanceName(rocketMQProperties.getInstanceName());

        //设置消费线程数大小取值范围。 [1, 1000]
        if (StringUtils.isNotBlank(consumerProperties.getConsumeThreadMin())) {
            this.consumer.setConsumeThreadMin(Integer.valueOf(consumerProperties.getConsumeThreadMin()));
        }
        if (StringUtils.isNotBlank(consumerProperties.getConsumeThreadMax())) {
            this.consumer.setConsumeThreadMax(Integer.valueOf(consumerProperties.getConsumeThreadMax()));
        }
        //消费者消费超时时间（分钟）
        if (consumerProperties.getConsumeTimeout() != null) {
            this.consumer.setConsumeTimeout(consumerProperties.getConsumeTimeout());
        }
        try {
            //订阅关系
            for (SubscribeProperties subscribe : consumerProperties.getSubscribes()) {
                this.consumer.subscribe(subscribe.getTopic(), subscribe.getExpression());
            }
            //创建MessageListener
            MessageListenerConcurrently messageListenerConcurrently = new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    if (msgs.size() != 1) {
                        throw new RuntimeException("收到多条消息");
                    }
                    return processingMessages(msgs.get(0), context, listener);
                }
            };
            // 注册回调实现类来处理从broker拉取回来的消息
            this.consumer.registerMessageListener(messageListenerConcurrently);
        } catch (MQClientException e) {
            throw new RuntimeException("RocketMQConsumer初始化异常", e);
        }
    }

    private ConsumeConcurrentlyStatus processingMessages(MessageExt message, ConsumeConcurrentlyContext context, RocketMQMessageListener listener) {
        //消息反序列化
        String msg = null;
        try {
            msg = new String(message.getBody(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("消息编码异常不再接收此条消息!请检查生产者消息序列化方式。topic={},listener:{},e:"
                    , message.getTopic(), listener.getClass().toString(), e);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        //消费消息
        RocketMQMessage rocketMQMessage = null;
        try {
            rocketMQMessage = RocketMQMessage.builder()
                    .message(msg)
                    .topic(message.getTopic())
                    .msgId(message.getMsgId())
                    .key(message.getKeys())
                    .tag(message.getTags())
                    .reconsumeTimes(message.getReconsumeTimes())
                    .build();
            boolean consume = listener.consume(rocketMQMessage);
            if (consume) {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } else {
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        } catch (Exception e) {
            log.error("消费异常请检查消费者代码!topic={},rocketMQMessage={},listener:{},e:"
                    , message.getTopic(), JSON.toJSON(rocketMQMessage), listener.getClass().toString(), e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }

    @Override
    public void start() {
        // 启动消费者实例
        try {
            this.consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        if (this.consumer != null) {
            this.consumer.shutdown();
        }
    }

}
