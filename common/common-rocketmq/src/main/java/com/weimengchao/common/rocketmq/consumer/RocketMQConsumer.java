package com.weimengchao.common.rocketmq.consumer;

/**
 * 消息消费者接口，用来订阅消息
 */
public interface RocketMQConsumer {

    /**
     * 启动服务
     */
    void start();

    /**
     * 关闭服务
     */
    void shutdown();

}
