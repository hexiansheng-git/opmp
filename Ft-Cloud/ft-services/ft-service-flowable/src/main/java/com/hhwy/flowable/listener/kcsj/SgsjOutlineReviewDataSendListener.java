package com.hhwy.flowable.listener.kcsj;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.flowable.feign.service.SdServiceApi;
import com.hhwy.flowable.feign.service.SpServiceApi;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * 功能描述:  勘察设计 - 勘察设计大纲评审 数据推送总部
 * 作者: fushudong
 * 时间: 2024/2/5
 */
public class SgsjOutlineReviewDataSendListener implements TaskListener, ExecutionListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String tenantId = delegateTask.getTenantId();
        SdServiceApi bean = SpringUtils.getBean(SdServiceApi.class);
        System.out.println("勘察设计 - 勘察设计大纲评审 总部数据推送");
        bean.outLineDoSendGm(tenantId);
    }

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String tenantId = delegateExecution.getTenantId();
        SdServiceApi bean = SpringUtils.getBean(SdServiceApi.class);
        System.out.println("勘察设计 - 勘察设计大纲评审 总部数据推送");
        bean.outLineDoSendGm(tenantId);
    }
}