package com.hhwy.flowable.listener.sgjs;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.flowable.feign.service.PmServiceApi;
import com.hhwy.flowable.feign.service.SpServiceApi;
import com.hhwy.flowable.listener.BaseExecutionListener;
import com.hhwy.flowable.listener.BaseTaskListener;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.util.Assert;

/**
 *  施工技术管理发送消息节点
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2024/4/19 10:41   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2024/4/19 10:41    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
public class SgjsDesignChangeOverListener extends BaseExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        Long id = init(delegateExecution);
        SpServiceApi bean = SpringUtils.getBean(SpServiceApi.class);
        AjaxResult result = bean.designChangeListListener(id);
        Assert.isTrue(AjaxResult.isSuccess(result),result.get(AjaxResult.MSG_TAG)==null?"":result.get(AjaxResult.MSG_TAG).toString());
    }

}
