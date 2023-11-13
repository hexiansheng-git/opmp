package com.hhwy.flowable.listener.qqch;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.flowable.feign.service.PmServiceApi;
import com.hhwy.flowable.listener.BaseExecutionListener;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.util.Assert;

/**
 * 前期策划变更审批流程监听器
 */
public class QqchChangeListener extends BaseExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        Long id = init(delegateExecution);

        PmServiceApi bean = SpringUtils.getBean(PmServiceApi.class);
        AjaxResult result = bean.qqchChangeFinish(Long.valueOf(id));
        Assert.isTrue(AjaxResult.isSuccess(result),result.get(AjaxResult.MSG_TAG)==null?"":result.get(AjaxResult.MSG_TAG).toString());
    }
}
