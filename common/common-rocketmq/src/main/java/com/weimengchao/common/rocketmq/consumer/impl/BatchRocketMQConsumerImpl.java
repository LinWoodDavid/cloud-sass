package com.weimengchao.common.rocketmq.consumer.impl;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQClientException;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.common.message.MessageExt;
import com.weimengchao.common.rocketmq.consumer.BatchRocketMQConsumer;
import com.weimengchao.common.rocketmq.domain.RocketMQMessage;
import com.weimengchao.common.rocketmq.listener.BatchRocketMQMessageListener;
import com.weimengchao.common.rocketmq.properties.ConsumerProperties;
import com.weimengchao.common.rocketmq.properties.RocketMQProperties;
import com.weimengchao.common.rocketmq.properties.SubscribeProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BatchRocketMQConsumerImpl implements BatchRocketMQConsumer {

    private DefaultMQPushConsumer consumer;

    public BatchRocketMQConsumerImpl(ConsumerProperties consumerProperties,
                                     RocketMQProperties rocketMQProperties,
                                     BatchRocketMQMessageListener batchRocketMQMessageListener) {
        // 实例化消费者
        this.consumer = new DefaultMQPushConsumer(consumerProperties.getGroupId());
        // 设置NameServer的地址
        this.consumer.setNamesrvAddr(rocketMQProperties.getNameSrvAddr());
        this.consumer.setInstanceName(rocketMQProperties.getInstanceName());
        //设置批量消费最大数
        if (StringUtils.isNotBlank(consumerProperties.getConsumeMessageBatchMaxSize())) {
            this.consumer.setConsumeMessageBatchMaxSize(Integer.valueOf(consumerProperties.getConsumeMessageBatchMaxSize()));
        }

        //设置消费线程数大小取值范围。 [1, 1000]
        if (StringUtils.isNotBlank(consumerProperties.getConsumeThreadMin())) {
            this.consumer.setConsumeThreadMin(Integer.valueOf(consumerProperties.getConsumeThreadMin()));
        }
        if (StringUtils.isNotBlank(consumerProperties.getConsumeThreadMax())) {
            this.consumer.setConsumeThreadMax(Integer.valueOf(consumerProperties.getConsumeThreadMax()));
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
                    return processingMessages(msgs, context, batchRocketMQMessageListener);
                }
            };
            // 注册回调实现类来处理从broker拉取回来的消息
            this.consumer.registerMessageListener(messageListenerConcurrently);
        } catch (MQClientException e) {
            throw new RuntimeException("BatchRocketMQConsumer初始化异常" + e);
        }
    }

    /**
     * 处理消息
     *
     * @param messageExts messageExts
     * @param context     context
     * @param listener    listener
     * @return ConsumeConcurrentlyStatus
     */
    private ConsumeConcurrentlyStatus processingMessages(List<MessageExt> messageExts, ConsumeConcurrentlyContext context, BatchRocketMQMessageListener listener) {
        //消息反序列化
        List<RocketMQMessage> messages = new ArrayList<>();
        try {
            for (MessageExt message : messageExts) {
                String msg = new String(message.getBody(), "utf-8");
                RocketMQMessage rocketMQMessage = RocketMQMessage.builder()
                        .message(msg)
                        .topic(message.getTopic())
                        .msgId(message.getMsgId())
                        .key(message.getKeys())
                        .tag(message.getTags())
                        .reconsumeTimes(message.getReconsumeTimes())
                        .build();
                messages.add(rocketMQMessage);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("消息编码异常不再接收本批条消息!请检查生产者消息序列化方式。共{}条，listener:{},e:"
                    , messageExts.size(), listener.getClass().toString(), e);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        //消费消息
        try {
            boolean consume = listener.consume(messages);
            if (consume) {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } else {
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        } catch (Exception e) {
            log.error("消费异常请检查消费者代码!messages={},listener:{},e:"
                    , JSON.toJSON(messages), listener.getClass().toString(), e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }

    @Override
    public void start() {
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
