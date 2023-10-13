package com.hhwy.flowable.listener.xmsl;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.flowable.feign.service.PmServiceApi;
import com.hhwy.flowable.listener.BaseExecutionListener;
import com.hhwy.flowable.listener.BaseTaskListener;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

public class DrawReviewListener extends BaseTaskListener{

    @Override
    public void notify(DelegateTask delegateTask) {
        Long id = init(delegateTask);

        PmServiceApi bean = SpringUtils.getBean(PmServiceApi.class);
        bean.drawReviewListener(id);
    }
}
