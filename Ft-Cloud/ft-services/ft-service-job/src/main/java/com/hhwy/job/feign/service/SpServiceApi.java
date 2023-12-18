package com.hhwy.job.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * sp服务
 *
 * @author lcf
 * @data 2023-12-18
 */
@FeignClient(name = "ft-service-sd")
public interface SpServiceApi {
    /**
     * 试验进场设备
     *
     * @return
     */
    @PostMapping("/jobController/getWuSheMaterialRecord")
    AjaxResult getWuSheMaterialRecord();


}
