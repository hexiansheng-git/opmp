package com.hhwy.job.task.sd.kcsj.planProcess.planProcess;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.job.feign.service.SdServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("planProcessTask")
public class PlanProcessTask {

    @Autowired
    private SdServiceApi sdServiceApi;

    private Logger logger= LoggerFactory.getLogger(PlanProcessTask.class);

    /**
     * 每天发预警消息
     */
    public void getPlanProcessTask(){
        logger.info("----------勘察设计--计划进度------------");
        AjaxResult result = sdServiceApi.jobPlanProcess();
    }
}
