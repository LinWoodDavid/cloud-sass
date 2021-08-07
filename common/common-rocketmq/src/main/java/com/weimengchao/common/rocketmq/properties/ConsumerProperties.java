package com.weimengchao.common.rocketmq.properties;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Properties;

/**
 * 消费者配置
 */
@Data
public class ConsumerProperties {

    //消费者配置
    private String groupId;
    /**
     * rocketMQ
     * 批量消费最大消息条数，取值范围: [1, 1024]。默认是1
     * <p>
     * 阿里云rocketMQ
     * BatchConsumer每次批量消费的最大消息数量, 默认值为1, 允许自定义范围为[1, 32], 实际消费数量可能小于该值.
     */
    private String consumeMessageBatchMaxSize;
    /**
     * 设置消费线程数大小取值范围都是 [1, 1000]。
     */
    private String consumeThreadMin;//默认是20
    private String consumeThreadMax;//默认是64

    /**
     * 消息超时时间 分钟（默认15分钟）
     */
    private Long consumeTimeout;

    //阿里云消费者配置
    private String consumeThreadNums;//消费者线程数

    private List<SubscribeProperties> subscribes;

    public Properties getConsumerProperties(RocketMQProperties rocketMQProperties) {
        Properties properties = rocketMQProperties.getRocketMQProperties();
        properties.setProperty(PropertyKeyConst.GROUP_ID, this.groupId);
        if (StringUtils.isNotBlank(this.consumeThreadNums)) {
            properties.setProperty(PropertyKeyConst.ConsumeThreadNums, this.consumeThreadNums);
        }
        //消费者超时时间
        if (this.consumeTimeout != null) {
            properties.setProperty(PropertyKeyConst.ConsumeTimeout, String.valueOf(this.consumeTimeout));
        }
        return properties;
    }

}
