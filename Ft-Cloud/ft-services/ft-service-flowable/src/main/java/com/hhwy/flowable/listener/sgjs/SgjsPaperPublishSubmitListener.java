package com.hhwy.flowable.listener.sgjs;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.flowable.feign.service.SpServiceApi;
import com.hhwy.flowable.listener.BaseExecutionListener;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.util.Assert;

/**
 * 前期策划工作小组审批流程监听器
 */
public class SgjsPaperPublishSubmitListener extends BaseExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(delegateExecution.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        AjaxResult result = bean.submitPaperPublishProcess(Long.valueOf(businessKey));
        Assert.isTrue(AjaxResult.isSuccess(result),result.get(AjaxResult.MSG_TAG)==null?"":result.get(AjaxResult.MSG_TAG).toString());
    }
}
