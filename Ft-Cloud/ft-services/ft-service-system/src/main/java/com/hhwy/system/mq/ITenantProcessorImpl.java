package com.hhwy.system.mq;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.flowable.api.RemoteBpmnSyncService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.core.processor.ITenantProcessor;
import com.hhwy.utils.exception.CustomBusinessException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class ITenantProcessorImpl implements ITenantProcessor {

    @Autowired
    private RocketMQTemplate   rocketMQTemplate;

    @Autowired
    PmServiceApi pmServiceApi;

    @Autowired
    RemoteBpmnSyncService remoteBpmnSyncService;

    @Override
    public void doPostForInsert(SysTenant sysTenant) {
//        System.out.println("租户创建成功回调方法开始********************************************************************************");
//        Map<String, Object> projectInfo = sysTenant.getParams();
//        rocketMQTemplate.convertAndSend("pm:tenantSuccess",projectInfo);
//        AjaxResult res = pmServiceApi.insertProjectTenant(projectInfo);
//        System.out.println("租户创建成功回调方法开始********************************************************************************");

//        if(!res.get("code").toString().equals("200")){
//            throw  new CustomBusinessException("同步项目信息到租户数据库失败！！");
//        };

        masterToTenant(sysTenant);
    }

    //给租户下发流程信息
    public void masterToTenant(SysTenant sysTenant) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tenantKey",sysTenant.getTenantKey());
        remoteBpmnSyncService.masterToTenant(jsonObject);
    }

    @Override
    public void doPostForUpdate(SysTenant sysTenant) {

    }

    @Override
    public void doPostForDelete(String s) {

    }
}
