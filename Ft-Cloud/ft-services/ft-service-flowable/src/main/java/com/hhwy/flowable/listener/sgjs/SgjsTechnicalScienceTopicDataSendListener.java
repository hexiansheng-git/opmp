package com.hhwy.flowable.listener.sgjs;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.flowable.feign.service.SpServiceApi;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * 功能：科技管理 - 科研课题申请 数据推送
 * 作者: fushudong
 * 时间: 2024/02/01
 */
public class SgjsTechnicalScienceTopicDataSendListener implements TaskListener, ExecutionListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String tenantId = delegateTask.getTenantId();
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        System.out.println("科技管理 - 科研课题 总部数据推送");
        bean.technicalTopicDoSendGm(tenantId);
    }

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String tenantId = delegateExecution.getTenantId();
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        System.out.println("科技管理 - 科研课题 总部数据推送");
        bean.technicalTopicDoSendGm(tenantId);
    }
}