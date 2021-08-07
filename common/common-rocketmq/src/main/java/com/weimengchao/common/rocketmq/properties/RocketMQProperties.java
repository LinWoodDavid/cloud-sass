package com.weimengchao.common.rocketmq.properties;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import lombok.Data;

import java.util.Properties;

/**
 * RocketMQ配置
 */
@Data
public class RocketMQProperties {

    /**
     * 默认本地rocketmq,当 type 为 aliyun 时为阿里云rocketmq配置
     */
    private String type;
    private String accessKey;
    private String secretKey;
    private String nameSrvAddr;
    private String onsChannel;
    private String instanceId;
    private String instanceName;

    public Properties getRocketMQProperties() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, this.accessKey);
        properties.setProperty(PropertyKeyConst.SecretKey, this.secretKey);
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, this.nameSrvAddr);
        properties.setProperty(PropertyKeyConst.OnsChannel, this.onsChannel);
        properties.setProperty(PropertyKeyConst.INSTANCE_ID, this.instanceId);
        properties.setProperty(PropertyKeyConst.InstanceName, this.instanceName);
        return properties;
    }

    /**
     * 判断环境是否是阿里云
     *
     * @return true阿里云rocketMQ，false:本地rocketMQ
     */
    public boolean isAliYun() {
        return "aliyun".equals(this.type);
    }

}
