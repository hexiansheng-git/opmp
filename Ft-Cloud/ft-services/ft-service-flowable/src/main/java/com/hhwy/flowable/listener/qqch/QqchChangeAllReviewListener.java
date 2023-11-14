package com.hhwy.flowable.listener.qqch;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.flowable.feign.service.PmServiceApi;
import com.hhwy.flowable.listener.BaseTaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.util.Assert;

/**
 * 前期策划变更所有变更人提交触发监听器
 */
public class QqchChangeAllReviewListener extends BaseTaskListener {


    @Override
    public void notify(DelegateTask delegateExecution) {
        Long id = init(delegateExecution);

        PmServiceApi bean = SpringUtils.getBean(PmServiceApi.class);
        AjaxResult result = bean.reviewAllFinishListener(Long.valueOf(id));
        Assert.isTrue(AjaxResult.isSuccess(result),result.get(AjaxResult.MSG_TAG)==null?"":result.get(AjaxResult.MSG_TAG).toString());
    }
}
