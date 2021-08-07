package com.weimengchao.common.rocketmq.listener;

import com.weimengchao.common.rocketmq.domain.RocketMQMessage;

import java.util.List;

/**
 * 批量消息监听器
 */
public interface BatchRocketMQMessageListener {

    boolean consume(List<RocketMQMessage> messages);

}
