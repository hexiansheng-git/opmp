package com.hhwy.flowable.listener.kcsj;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.flowable.feign.service.SdServiceApi;
import com.hhwy.flowable.listener.BaseExecutionListener;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.util.Assert;

/**
 * 前期策划工作小组审批流程监听器
 */
public class KcsjAchievementReviewListener extends BaseExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(delegateExecution.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();

        SdServiceApi bean = SpringUtils.getBean(SdServiceApi.class);
        AjaxResult result = bean.updateKcsjAchievementReviewProcess(Long.valueOf(businessKey));
        Assert.isTrue(AjaxResult.isSuccess(result),result.get(AjaxResult.MSG_TAG)==null?"":result.get(AjaxResult.MSG_TAG).toString());
    }
}
