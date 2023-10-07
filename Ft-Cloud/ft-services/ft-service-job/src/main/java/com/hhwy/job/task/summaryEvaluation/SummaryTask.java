package com.hhwy.job.task.summaryEvaluation;

import com.hhwy.job.feign.service.PmServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("SummaryTask")
public class SummaryTask {

    @Autowired
    private PmServiceApi pmServiceApi;

    /**
     * 前期策划总结评价-总结预警
     */
    public void summarySetUpWarn() {
        System.out.println("----------发送预警------------");
        pmServiceApi.summarySetUpWarn();
    }
}
