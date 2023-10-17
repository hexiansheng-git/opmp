package com.hhwy.flowable.listener.xmsl;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.flowable.feign.service.PmServiceApi;
import com.hhwy.flowable.listener.BaseExecutionListener;
import com.hhwy.flowable.listener.BaseTaskListener;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.util.Assert;

public class DrawReviewListener extends BaseTaskListener{

    @Override
    public void notify(DelegateTask delegateTask) {
        Long id = init(delegateTask);

        PmServiceApi bean = SpringUtils.getBean(PmServiceApi.class);
        AjaxResult result = bean.drawReviewListener(id);
        Assert.isTrue(AjaxResult.isSuccess(result),result.get(AjaxResult.MSG_TAG)==null?"":result.get(AjaxResult.MSG_TAG).toString());
    }
}
