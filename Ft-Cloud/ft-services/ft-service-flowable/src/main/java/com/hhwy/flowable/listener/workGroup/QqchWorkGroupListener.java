package com.hhwy.flowable.listener.workGroup;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.flowable.feign.service.PmServiceApi;
import com.hhwy.flowable.listener.BaseExecutionListener;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;

import java.util.Map;

/**
 * 前期策划工作小组审批流程监听器
 */
public class QqchWorkGroupListener extends BaseExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(delegateExecution.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();

        Map<String, Object> variables = delegateExecution.getVariables();
        String s = JSONObject.toJSONString(variables);
        PmServiceApi bean = SpringUtils.getBean(PmServiceApi.class);
        bean.updateWorkGroupProcess(Long.valueOf(businessKey));
    }
}
