package com.hhwy.pm.mq;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.pm.gm.wbs.domain.TWbs;
import com.hhwy.pm.gm.wbs.service.ITWbsService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  标准wbs主表信息同步 
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2023/8/18 11:31   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2023/8/18 11:31    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
@Service
@RocketMQMessageListener(
        consumerGroup = "pm-project-h2",
        topic = "gm",
        selectorExpression = "wbsMain",
        consumeMode = ConsumeMode.ORDERLY)
public class TWbsMainConsumerListener implements RocketMQListener<String> , RocketMQPushConsumerLifecycleListener {
    @Autowired
    private ITWbsService wbsService;

    @Override
    public void onMessage(String s) {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            Map map = JSONObject.parseObject(s, Map.class);
            wbsService.insertTWbsMain(map);
            if(ObjectUtils.isNotBlank(map.get("ptVar1")))
                wbsService.deleteTWbsMain(ObjectUtils.nvlLong(map.get("ptVar1")));
        }catch (Exception e){
            throw new CustomBusinessException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setMaxReconsumeTimes(3);
        defaultMQPushConsumer.setInstanceName("mqconsumer1");
    }
}
