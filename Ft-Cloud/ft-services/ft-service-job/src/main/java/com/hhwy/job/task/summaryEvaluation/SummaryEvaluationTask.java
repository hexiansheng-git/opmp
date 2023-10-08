package com.hhwy.job.task.summaryEvaluation;

import com.hhwy.job.feign.service.PmServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("SummaryEvaluationTask")
public class SummaryEvaluationTask {

    @Autowired
    private PmServiceApi pmServiceApi;

    /**
     * 前期策划总结评价-总结预警
     */
    public void summaryWarn() {
        System.out.println("----------发送预警------------");
        pmServiceApi.summaryWarn();
    }

    /**
     * 前期策划总结评价-总结预警
     */
    public void evaluationWarn() {
        System.out.println("----------发送预警------------");
        pmServiceApi.evaluationWarn();
    }
}
