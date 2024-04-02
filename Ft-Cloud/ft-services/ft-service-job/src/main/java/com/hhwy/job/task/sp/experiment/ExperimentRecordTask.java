package com.hhwy.job.task.sp.experiment;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.job.feign.service.SpServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("experimentRecordTask")
public class ExperimentRecordTask {

    @Autowired
    private SpServiceApi spServiceApi;

    /**
     * 每天发预警消息
     */
    public void getExperimentRecordTask(){
        System.out.println("----------施工技术--设备台账及检验报告---预警预警--------");
        AjaxResult result = spServiceApi.experimentRecordJob();

    }

}
