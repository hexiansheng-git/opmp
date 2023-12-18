package com.hhwy.job.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * sa服务
 *
 * @author hhwy
 */
@FeignClient(name = "ft-service-sd")
public interface SdServiceApi {

    @PostMapping("/kcsjPlanMonthlyReport/generateMonthlyReport")
    AjaxResult generateMonthlyReport();
}
