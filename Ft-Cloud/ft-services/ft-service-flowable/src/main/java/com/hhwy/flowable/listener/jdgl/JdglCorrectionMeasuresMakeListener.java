package com.hhwy.flowable.listener.jdgl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.flowable.feign.service.PmServiceApi;
import java.util.Map;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * @author zhenglili
 * @date 2023-09-14
 * @remark 纠偏措施制定审批流程监听器
 */
public class JdglCorrectionMeasuresMakeListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
            .processInstanceId(delegateTask.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();

        Map<String, Object> variables = delegateTask.getVariables();
        String s = JSONObject.toJSONString(variables);
        PmServiceApi bean = SpringUtils.getBean(PmServiceApi.class);
        bean.updateJdglCorrectionMeasuresMakeProcess(Long.valueOf(businessKey));
    }
}
