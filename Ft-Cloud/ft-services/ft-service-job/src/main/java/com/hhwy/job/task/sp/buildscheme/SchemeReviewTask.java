package com.hhwy.job.task.sp.buildscheme;

import com.hhwy.job.feign.service.SpServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("SchemeReviewTask")
@Slf4j
public class SchemeReviewTask {

    @Autowired
    private SpServiceApi spServiceApi;

    /**
     * 每天发预警消息
     */
    public void schemeReviewWarn(){
        log.info("----------施工技术--施工方案评审预警--------");
        spServiceApi.warnMessageSchemeReview();

    }

}
