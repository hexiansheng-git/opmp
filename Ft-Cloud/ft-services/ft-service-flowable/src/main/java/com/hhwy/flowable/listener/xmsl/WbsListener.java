package com.hhwy.flowable.listener.xmsl;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.flowable.feign.service.PmServiceApi;
import com.hhwy.flowable.listener.BaseExecutionListener;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * wbs执行监听
 */
public class WbsListener extends BaseExecutionListener {

    @Override
    public void notify(DelegateExecution delegateTask) {
        Long id = init(delegateTask);

        PmServiceApi bean = SpringUtils.getBean(PmServiceApi.class);
        AjaxResult result = bean.wbsMainListener(id);
        Assert.isTrue(AjaxResult.isSuccess(result),result.get(AjaxResult.MSG_TAG)==null?"":result.get(AjaxResult.MSG_TAG).toString());
    }
}
