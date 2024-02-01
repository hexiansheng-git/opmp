package com.hhwy.flowable.listener.sgjs;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.flowable.feign.service.PmServiceApi;
import com.hhwy.flowable.feign.service.SpServiceApi;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;

import java.util.Map;

/**
 * 功能：科技管理 - 科研课题立项
 * 作者: fushudong
 * 时间: 2024/02/01
 */
public class SgsjTechnicalScienceTopicListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(delegateTask.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        bean.updateTaskStatus(Long.valueOf(businessKey));
    }
}