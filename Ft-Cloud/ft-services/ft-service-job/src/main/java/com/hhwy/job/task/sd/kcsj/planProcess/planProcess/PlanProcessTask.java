package com.hhwy.job.task.sd.kcsj.planProcess.planProcess;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.job.feign.service.SdServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("planProcessTask")
public class PlanProcessTask {

    @Autowired
    private SdServiceApi sdServiceApi;

    /**
     * 每天发预警消息
     */
    public void getPlanProcessTask(){
        System.out.println("----------勘察设计--计划进度------------");
        AjaxResult result = sdServiceApi.jobPlanProcess();
    }
}
