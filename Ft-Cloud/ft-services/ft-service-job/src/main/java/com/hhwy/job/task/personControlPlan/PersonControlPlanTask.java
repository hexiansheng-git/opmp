package com.hhwy.job.task.personControlPlan;

import com.hhwy.job.feign.service.PmServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("PersonControlPlanTask")
public class PersonControlPlanTask {

    @Autowired
    private PmServiceApi pmServiceApi;

    /**
     * 人员管控策划预警
     */
    public void personControlPlanWarn() {
        System.out.println("----------发送预警------------");
        pmServiceApi.personControlPlanWarn();
    }
}
