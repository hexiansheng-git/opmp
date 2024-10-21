package com.hhwy.flowable.listener.sgjs;/*
 * @Description: TODO
 * @Author: $
 * @Date: $
 **/

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.flow.TaskResourceNew;
import com.hhwy.feign.service.FlowServiceApi;
import com.hhwy.flowable.feign.service.SpServiceApi;
import com.hhwy.utils.ObjectUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SgjsBuildSchemeReviewCheckListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(delegateTask.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        SpServiceApi spServiceApi = SpringUtils.getBean(SpServiceApi.class);
        String taskId = delegateTask.getId();
        FlowServiceApi flowServiceApi = SpringUtils.getBean(FlowServiceApi.class);
        AjaxResult result = flowServiceApi.taskInfoDetail(taskId);
        Assert.isTrue(AjaxResult.isSuccess(result) ,"获取流程信息失败");
        TaskResourceNew taskResourceNew = JSONObject.parseObject(JSONObject.toJSONString(result.getData()), TaskResourceNew.class) ;
        List<String> nodeMarkList = taskResourceNew.getCustomProperties().get("flowNodeMark");
        AjaxResult checkAjaxresult = spServiceApi.checkAuditOpinon(nodeMarkList.get(0),delegateTask.getAssignee(),Long.valueOf(businessKey));
        if(!AjaxResult.isSuccess(checkAjaxresult)){
            String msg = ObjectUtils.nvlString(checkAjaxresult.get(AjaxResult.MSG_TAG));
            throw new RuntimeException(msg);
        }
    }
}
