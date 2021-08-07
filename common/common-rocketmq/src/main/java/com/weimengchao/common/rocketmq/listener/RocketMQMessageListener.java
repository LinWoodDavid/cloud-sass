package com.weimengchao.common.rocketmq.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weimengchao.common.rocketmq.domain.RocketMQMessage;

/**
 * 单条消息监听器
 */
public interface RocketMQMessageListener {

    boolean consume(RocketMQMessage message) throws Exception;

}
