package com.hhwy.flowable.listener;

import com.hhwy.common.core.utils.SpringUtils;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
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
public class BaseExecutionListener implements ExecutionListener {

    protected  Map<String, Object> variables;


    final protected Long init(DelegateExecution delegateExecution){
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(delegateExecution.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        variables = delegateExecution.getVariables();
        return Long.valueOf(businessKey);
    }

    @Override
    public void notify(DelegateExecution delegateExecution) {

    }
}
