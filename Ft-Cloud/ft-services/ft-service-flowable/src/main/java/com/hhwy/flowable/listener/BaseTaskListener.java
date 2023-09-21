package com.hhwy.flowable.listener;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;

import java.util.Map;

/**
 *  
  * <br/>@Author:       wk
  * <br/>@CreateDate:   2023/9/19 11:25   
  * <br/>@UpdateUser:   wk   
  * <br/>@UpdateDate:   2023/9/19 11:25    
  * <br/>@UpdateRemark: 说明本次修改内容 
  * <br/>@Version:      v1.0    
  */
public class BaseTaskListener implements TaskListener {

    protected  Map<String, Object> variables;


    final protected Long init(DelegateTask delegateTask){
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(delegateTask.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        variables = delegateTask.getVariables();
        return Long.valueOf(businessKey);
    }

    @Override
    public void notify(DelegateTask delegateTask) {
    }
}
