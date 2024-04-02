package com.hhwy.job.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * sp服务
 *
 * @author lcf
 * @data 2023-12-18
 */
@FeignClient(name = "ft-service-sd")
public interface SpServiceApi {
    /**
     * 施工技术---试验进场设备
     *
     * @return
     */
    @GetMapping("/jobController/getWuSheMaterialRecord")
    AjaxResult getWuSheMaterialRecord();

    /**
     * 施工技术---测量进场设备
     *
     * @return
     */
    @GetMapping("/jobController/getWuSheMeasureMaterialInfo")
    AjaxResult syncWusheJob();

    /**
     * 施工技术---试验进场设备
     * 发预警发预警发预警!!!!!!
     *
     * @return
     * @author lcf
     * @date 2024-04-01
     */
    @GetMapping("/sgjsExperimentRecord/experimentRecordJob")
    AjaxResult experimentRecordJob();

}
