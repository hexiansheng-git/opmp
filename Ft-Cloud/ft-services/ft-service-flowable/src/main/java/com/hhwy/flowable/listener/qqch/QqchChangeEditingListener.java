package com.hhwy.flowable.listener.qqch;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.flowable.feign.service.PmServiceApi;
import com.hhwy.flowable.listener.BaseExecutionListener;
import com.hhwy.flowable.listener.BaseTaskListener;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 前期策划变更编制人提交触发监听器
 */
public class QqchChangeEditingListener extends BaseTaskListener {


    @Override
    public void notify(DelegateTask delegateExecution) {
        Long id = init(delegateExecution);

        PmServiceApi bean = SpringUtils.getBean(PmServiceApi.class);
        AjaxResult result = bean.qqchChangeEditFinish(Long.valueOf(id));
        Assert.isTrue(AjaxResult.isSuccess(result),result.get(AjaxResult.MSG_TAG)==null?"":result.get(AjaxResult.MSG_TAG).toString());
    }
}
