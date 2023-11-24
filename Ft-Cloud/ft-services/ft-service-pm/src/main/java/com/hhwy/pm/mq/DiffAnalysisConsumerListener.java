package com.hhwy.pm.mq;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglWarnRecord;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglWarnRecordService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 */
@Service
@RocketMQMessageListener(
        consumerGroup = "jdgl_warn_record",
        topic = "jdgl_warn_record",
        selectorExpression = "insert",
        consumeMode = ConsumeMode.ORDERLY)
public class DiffAnalysisConsumerListener implements RocketMQListener<String> , RocketMQPushConsumerLifecycleListener {

    @Autowired
    private IJdglWarnRecordService jdglWarnRecordService;

    @Override
    public void onMessage(String s) {
        String oldDataSource = null;
        try {
            JdglWarnRecord warnRecord = JSONObject.parseObject(s, JdglWarnRecord.class);
            if(warnRecord == null){
                return;
            }
            String tenantKey = warnRecord.getProjectCode();
            String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
            oldDataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey("master");
            if(StringUtils.isNotBlank(dataSource) && !dataSource.equals(oldDataSource)){
                DynamicDataSourceContextHolder.push(dataSource);
                jdglWarnRecordService.insertJdglWarnRecord(warnRecord);
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setMaxReconsumeTimes(3);
        defaultMQPushConsumer.setInstanceName("jdgl_warn_record");
    }
}
