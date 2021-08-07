package com.weimengchao.common.rocketmq.config;

import com.weimengchao.common.rocketmq.RocketMQBuilder;
import com.weimengchao.common.rocketmq.producer.RocketMQProducer;
import com.weimengchao.common.rocketmq.properties.ProducerProperties;
import com.weimengchao.common.rocketmq.properties.RocketMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @ConditionalOnBean //	当给定的在bean存在时,则实例化当前Bean
 * @ConditionalOnMissingBean //	当给定的在bean不存在时,则实例化当前Bean
 * @ConditionalOnClass //	当给定的类名在类路径上存在，则实例化当前Bean
 * @ConditionalOnMissingClass //	当给定的类名在类路径上不存在，则实例化当前Bean
 */
@Slf4j
public class RocketMQAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "rocketmq", name = "nameSrvAddr")
    @ConfigurationProperties(prefix = "rocketmq")
    public RocketMQProperties rocketMQProperties() {
        return new RocketMQProperties();
    }

    //—————————————————————————————————— 生产者 ——————————————————————————————————
    @Bean
    @ConditionalOnProperty(prefix = "rocketmq.producer", name = "producerGroup")
    @ConfigurationProperties(prefix = "rocketmq.producer")
    public ProducerProperties producerProperties() {
        return new ProducerProperties();
    }

    /**
     * 向spring容器注入RocketMQ生产者
     *
     * @param producerProperties producerProperties
     * @return
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    @ConditionalOnBean(value = {RocketMQProperties.class, ProducerProperties.class})
    public RocketMQProducer rocketMQProducer(@Autowired ProducerProperties producerProperties,
                                             @Autowired RocketMQProperties rocketMQProperties) {
        return RocketMQBuilder.builder(producerProperties, rocketMQProperties);
    }

//—————————————————————————————————— 消费者 ——————————————————————————————————

//    @Bean
//    @ConfigurationProperties(prefix = "rocketmq.consumer")
//    public ConsumerProperties consumerProperties() {
//        return new ConsumerProperties();
//    }
//
//    @Bean
//    public MessageListener messageListener() {
//        return new MessageListener();
//    }
//
//    /**
//     * 向spring容器注入RocketMQ消费者
//     *
//     * @param consumerProperties consumerProperties
//     * @param listener           listener
//     * @return
//     */
//    @Bean(initMethod = "start", destroyMethod = "shutdown")
//    @ConditionalOnBean({ConsumerProperties.class, RocketMQMessageListener.class, RocketMQProperties.class})
//    public RocketMQConsumer rocketMQConsumer(@Autowired ConsumerProperties consumerProperties,
//                                             @Autowired RocketMQMessageListener listener,
//                                             @Autowired RocketMQProperties rocketMQProperties) {
//        return RocketMQBuilder.builder(consumerProperties, rocketMQProperties, listener);
//    }

    //—————————————————————————————————— 批量消费者 ——————————————————————————————————
//    @Bean
//    public BatchMessageListener batchMessageListener() {
//        return new BatchMessageListener();
//    }
//
//
//    /**
//     * 向spring容器注入RocketMQ批量消费者
//     *
//     * @param consumerProperties consumerProperties
//     * @param listener           listener
//     * @return
//     */
//    @Bean(initMethod = "start", destroyMethod = "shutdown")
//    @ConditionalOnBean({ConsumerProperties.class, RocketMQMessageListener.class, RocketMQProperties.class})
//    public BatchRocketMQConsumer batchRocketMQConsumer(@Autowired ConsumerProperties consumerProperties,
//                                                       @Autowired BatchRocketMQMessageListener listener,
//                                                       @Autowired RocketMQProperties rocketMQProperties) {
//        return RocketMQBuilder.builder(consumerProperties, rocketMQProperties, listener);
//    }

}
