package com.hhwy.job.task.review;

import com.hhwy.job.feign.service.PmServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ReviewTask")
public class ReviewTask {

    @Autowired
    private PmServiceApi pmServiceApi;

    /**
     * 前期策划编制第一阶段预警
     */
    public void preparationFirstStageWarn(){
        System.out.println("----------前期策划编制第一阶段预警------------");
        pmServiceApi.preparationFirstStageWarn();
    }

    /**
     * 前期策划编制第二阶段预警
     */
    public void preparationSecondStageWarn(){
        System.out.println("----------前期策划编制第二阶段预警------------");
        pmServiceApi.preparationSecondStageWarn();
    }

    /**
     * 前期策划编制第三阶段预警
     */
    public void preparationThirdStageWarn(){
        System.out.println("----------前期策划编制第三阶段预警------------");
        pmServiceApi.preparationThirdStageWarn();
    }
}
