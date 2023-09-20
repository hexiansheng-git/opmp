package com.hhwy.pm.mq;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
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
 * 项目信息同步
 */
@Service
@RocketMQMessageListener(
        consumerGroup = "pm-project-g1",
        topic = "gm",
        selectorExpression = "updatePrj",
        consumeMode = ConsumeMode.ORDERLY)
public class ProjectBasicInfoConsumerListener implements RocketMQListener<String> , RocketMQPushConsumerLifecycleListener {

    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;

    @Override
    public void onMessage(String s) {
        String oldDataSource = null;
        try {
            XmslProjectBasicInfo projectBasicInfo = JSONObject.parseObject(s, XmslProjectBasicInfo.class);
            String tenantKey = projectBasicInfo.getProjectCode();
            String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
            oldDataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey("master");
            if(StringUtils.isNotBlank(dataSource) && !dataSource.equals(oldDataSource)){
                DynamicDataSourceContextHolder.push(dataSource);
                projectBasicInfoService.updateProjectBasicInfo(projectBasicInfo);
            }
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
        defaultMQPushConsumer.setInstanceName("gm");
    }
}
