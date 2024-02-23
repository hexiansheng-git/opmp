package com.hhwy.flowable.listener.sgjs;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.flowable.feign.service.SpServiceApi;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * 功能：科技管理 - 科研课题申请 流程状态修改
 * 作者: fushudong
 * 时间: 2024/02/01
 */
public class SgsjTechnicalScienceTopicApplyListener implements TaskListener, ExecutionListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(delegateTask.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        System.out.println("科技管理 - 科研课题申请 流程状态修改");
        bean.appplyListener(Long.valueOf(businessKey));
    }

    @Override
    public void notify(DelegateExecution delegateExecution) {
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(delegateExecution.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        System.out.println("科技管理 - 科研课题申请 流程状态修改");
        bean.appplyListener(Long.valueOf(businessKey));
    }
}