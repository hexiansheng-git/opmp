package com.hhwy.system.mq;

import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.core.processor.ITenantProcessor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class ITenantProcessorImpl implements ITenantProcessor {

    @Autowired
    private RocketMQTemplate   rocketMQTemplate;

    @Autowired
    PmServiceApi pmServiceApi;

    @Override
    public void doPostForInsert(SysTenant sysTenant) {
        Map<String, Object> projectInfo = sysTenant.getParams();
        rocketMQTemplate.convertAndSend("pm:tenantSuccess",projectInfo);
        pmServiceApi.insertProjectTenant(projectInfo);
    }

    @Override
    public void doPostForUpdate(SysTenant sysTenant) {

    }

    @Override
    public void doPostForDelete(String s) {

    }
}
