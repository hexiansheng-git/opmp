package com.hhwy.pm.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisCorrect;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisCorrectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 */
@Service
@RocketMQMessageListener(
        consumerGroup = "diff_analysis_correct",
        topic = "diff_analysis_correct",
        selectorExpression = "update",
        consumeMode = ConsumeMode.ORDERLY)
@Slf4j
public class DiffAnalysisCorrectConsumerListener implements RocketMQListener<String> , RocketMQPushConsumerLifecycleListener {

    @Autowired
    private IJdglDiffAnalysisCorrectService jdglDiffAnalysisCorrectService;

    @Override
    public void onMessage(String s) {
        String oldDataSource = null;
        try {
            log.info("差异化分析修正得分推送数据: {}", s);
            List<JdglDiffAnalysisCorrect> correctList = JSONObject.parseArray(s, JdglDiffAnalysisCorrect.class);
            if(CollectionUtils.isEmpty(correctList)){
                return;
            }
            String tenantKey = correctList.get(0).getTenantKey();
            String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
            oldDataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey("master");
            if(StringUtils.isNotBlank(dataSource) && !dataSource.equals(oldDataSource)){
                DynamicDataSourceContextHolder.push(dataSource);
                jdglDiffAnalysisCorrectService.insertJdglDiffAnalysisCorrectList(correctList);
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
        defaultMQPushConsumer.setInstanceName("diff_analysis_correct");
    }
}
