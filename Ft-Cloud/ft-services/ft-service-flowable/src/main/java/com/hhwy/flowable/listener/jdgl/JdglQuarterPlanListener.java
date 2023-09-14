package com.hhwy.flowable.listener.jdgl;


import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.flowable.feign.service.PmServiceApi;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;

import java.util.Map;


/**
 * 功能：季进度计划审批流程监听器
 * 作者: cjh
 * 时间: 2023/09/01
 */
public class JdglQuarterPlanListener implements TaskListener{

    @Override
    public void notify(DelegateTask delegateTask) {
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(delegateTask.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();

        Map<String, Object> variables = delegateTask.getVariables();
        String s = JSONObject.toJSONString(variables);
        System.out.println(s);
        System.out.println(businessKey);
        PmServiceApi bean = SpringUtils.getBean(PmServiceApi.class);
        bean.updateJdglQuarterPlan(Long.valueOf(businessKey));
    }                                               
}