package com.weimengchao.common.rocketmq.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@Data
public class ProducerProperties {

    //本地配置
    private String producerGroup;

    public Properties getProducerProperties(RocketMQProperties rocketMQProperties) {
        return rocketMQProperties.getRocketMQProperties();
    }

}
