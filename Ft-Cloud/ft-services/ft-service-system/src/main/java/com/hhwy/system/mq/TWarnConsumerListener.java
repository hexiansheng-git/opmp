package com.hhwy.system.mq;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.system.warn.service.ITWarnService;
import com.hhwy.utils.exception.CustomBusinessException;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(
        consumerGroup = "gm_t_warn",
        topic = "gm_t_warn",
        selectorExpression = "tenantSuccess",
        // 消费模式: 顺序消费
        consumeMode = ConsumeMode.ORDERLY)
public class TWarnConsumerListener implements RocketMQListener<String> , RocketMQPushConsumerLifecycleListener {

    @Autowired
    private ITWarnService tWarnService;

    @Override
    public void onMessage(String s) {
        try {
            TWarn tWarn = JSONObject.parseObject(s, TWarn.class);
            if(tWarn == null){
                return;
            }
            tWarnService.pushTWarn(tWarn);
        }catch (Exception e){
            throw new CustomBusinessException(e.getMessage());
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setMaxReconsumeTimes(3);
        defaultMQPushConsumer.setInstanceName("gm_t_warn");
    }
}
