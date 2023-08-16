package com.hhwy.pm.mq;/*
 * @Description: TODO
 * @Author: $
 * @Date: $
 **/

import com.alibaba.fastjson.JSON;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.utils.exception.CustomBusinessException;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

/**
 * 消费消息
 * 配置RocketMQ监听
 * @author qzz
 */
@Service
@RocketMQMessageListener(
        consumerGroup = "pm-project",
        topic = "gm",
        selectorExpression = "prj",
        // 消费模式: 顺序消费
        consumeMode = ConsumeMode.ORDERLY)
public class RocketMQConsumerListener implements RocketMQListener<String> , RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(String s) {
        try {
            XmslProjectBasicInfo parse =  JSON.parseObject(s, XmslProjectBasicInfo.class);
            System.out.println("消费消息："+s);
            //接收参数转换为项目信息入库。
        }catch (Exception e){
            throw new CustomBusinessException(e.getMessage());
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setMaxReconsumeTimes(3);
        defaultMQPushConsumer.setInstanceName("mqconsumer1");
    }
}
