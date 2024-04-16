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

public class SgjsFourNewsAchievementDataSendListener implements TaskListener, ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateTask) {
        String tenantId = delegateTask.getTenantId();
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        System.out.println("科技管理 - 四新 总部数据推送");
        bean.fourNewsDoSendGm(tenantId);
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        String tenantId = delegateTask.getTenantId();
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        System.out.println("科技管理 - 四新 总部数据推送");
        bean.fourNewsDoSendGm(tenantId);
    }
}
