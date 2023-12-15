package com.hhwy.job.task.change;

import com.hhwy.job.feign.service.PmServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ChangeTask")
public class ChangeTask {

    @Autowired
    private PmServiceApi pmServiceApi;


    /**
     * 前期策划工作计划审批预警
     */
    public void changeApprovalWarn(){
        System.out.println("----------发送预警------------");
        pmServiceApi.changeApprovalWarn();
    }
}
