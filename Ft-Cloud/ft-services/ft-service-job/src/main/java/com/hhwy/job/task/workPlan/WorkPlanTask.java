package com.hhwy.job.task.workPlan;

import com.hhwy.job.feign.service.PmServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("WorkPlanTask")
public class WorkPlanTask {

    @Autowired
    private PmServiceApi pmServiceApi;

    /**
     * 前期策划工作计划提交预警
     */
    public void workPlanCommitWarn(){
        System.out.println("----------发送预警------------");
        pmServiceApi.workPlanCommitWarn();
    }

    /**
     * 前期策划工作计划审批预警
     */
    public void workPlanApprovalWarn(){
        System.out.println("----------发送预警------------");
        pmServiceApi.workPlanApprovalWarn();
    }
}
