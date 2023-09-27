package com.hhwy.job.task.workGroup;

import com.hhwy.job.feign.service.PmServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("WorkGroupTask")
public class WorkGroupTask {

    @Autowired
    private PmServiceApi pmServiceApi;

    /**
     * 前期策划工作小组设立预警
     */
    public void workGroupSetUpWarn(){
        System.out.println("----------发送预警------------");
        pmServiceApi.workGroupSetUpWarn();
    }
}
