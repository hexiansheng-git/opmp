package com.hhwy.pm.mq;/*
 * @Description: TODO
 * @Author: $
 * @Date: $
 **/

import com.alibaba.fastjson.JSON;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.exception.CustomBusinessException;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消费消息
 * 配置RocketMQ监听
 * @author qzz
 */
//@Service
//@RocketMQMessageListener(
//        consumerGroup = "pm-project-h",
//        topic = "pm",
//        selectorExpression = "prj",
//        // 消费模式: 顺序消费
//        consumeMode = ConsumeMode.ORDERLY)
public class RocketMQConsumerListener implements RocketMQListener<String> , RocketMQPushConsumerLifecycleListener {

    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;
    @Override
    public void onMessage(String s) {
        try {
            XmslProjectBasicInfo projectBasicInfo =  JSON.parseObject(s, XmslProjectBasicInfo.class);
            //接收参数转换为项目信息入库。
            xmslProjectBasicInfoService.insertProjectInvokeProject(projectBasicInfo);
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
