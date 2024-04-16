package com.hhwy.flowable.listener.sgjs;

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
 * 功能：施工方案管理 - 方案清单 数据推送总部
 * 作者: fushudong
 * 时间: 2024/02/01
 */
public class SgjsBuildSchemeListDataSendListener implements TaskListener, ExecutionListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String tenantId = delegateTask.getTenantId();
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        System.out.println("施工方案管理 - 施工方案清单 总部数据推送");
        bean.schemeListDoSendGm(tenantId);
    }

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String tenantId = delegateExecution.getTenantId();
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        System.out.println("施工方案管理 - 施工方案清单 总部数据推送");
        bean.schemeListDoSendGm(tenantId);
    }
}