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

public class SgjsFourNewsAchievementListener  implements TaskListener, ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateTask) {
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(delegateTask.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();

        Map<String, Object> variables = delegateTask.getVariables();
        String s = JSONObject.toJSONString(variables);
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        bean.updateFourNewsAchievement(Long.valueOf(businessKey));
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(delegateTask.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();

        Map<String, Object> variables = delegateTask.getVariables();
        String s = JSONObject.toJSONString(variables);
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        bean.updateFourNewsAchievement(Long.valueOf(businessKey));
    }
}
