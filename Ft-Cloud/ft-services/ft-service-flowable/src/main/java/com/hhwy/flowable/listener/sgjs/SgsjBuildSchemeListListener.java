package com.hhwy.flowable.listener.sgjs;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.flowable.feign.service.SpServiceApi;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;

import java.util.Map;

/**
 * 功能：施工方案管理 - 方案清单 流程状态修改
 * 作者: fushudong
 * 时间: 2024/02/01
 */
public class SgsjBuildSchemeListListener implements TaskListener, ExecutionListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(delegateTask.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        Map<String, Object> variables = delegateTask.getVariables();
        String isPass = variables.get("isPass") == null ? "" : (String)variables.get("isPass");
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        System.out.println("施工方案管理 - 施工方案清单 流程状态修改");
        bean.updateBuildScheme(Long.valueOf(businessKey), isPass);
    }

    @Override
    public void notify(DelegateExecution delegateExecution) {
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(delegateExecution.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        Map<String, Object> variables = delegateExecution.getVariables();
        String isPass = variables.get("isPass") == null ? "" : (String)variables.get("isPass");
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        System.out.println("施工方案管理 - 施工方案清单 流程状态修改");
        bean.updateBuildScheme(Long.valueOf(businessKey), isPass);
    }
}