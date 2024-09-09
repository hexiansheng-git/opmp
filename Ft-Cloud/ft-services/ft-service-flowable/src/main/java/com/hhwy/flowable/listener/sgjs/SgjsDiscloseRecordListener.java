package com.hhwy.flowable.listener.sgjs;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.flowable.feign.service.SpServiceApi;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一、二级方案交底监听
 *
 * @author lcf
 * @date 2024-09-09
 */
public class SgjsDiscloseRecordListener implements TaskListener, ExecutionListener {

    private Logger logger= LoggerFactory.getLogger(SgjsDiscloseRecordListener.class);

    @Override
    public void notify(DelegateExecution delegateExecution) {
        logger.info("开始执行1");
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(delegateExecution.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        SpServiceApi spServiceApi = SpringUtils.getBean(SpServiceApi.class);
        spServiceApi.disCloseRecordListener(Long.valueOf(businessKey),"4");
        logger.info("执行结束1");
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        logger.info("开始执行2");
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(delegateTask.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        SpServiceApi spServiceApi = SpringUtils.getBean(SpServiceApi.class);
        spServiceApi.disCloseRecordListener(Long.valueOf(businessKey),"4");
        logger.info("执行结束2");
    }
}
