package com.hhwy.flowable.listener.xmsl;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.flowable.feign.service.PmServiceApi;
import com.hhwy.flowable.listener.BaseExecutionListener;
import org.flowable.engine.delegate.DelegateExecution;

public class DrawReviewListener extends BaseExecutionListener {

    @Override
    public void notify(DelegateExecution delegateTask) {
        Long id = init(delegateTask);

        PmServiceApi bean = SpringUtils.getBean(PmServiceApi.class);
        bean.drawReviewListener(id);
    }
}
