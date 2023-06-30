package com.hhwy.pm.api;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.api.factory.RemoteDemoFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 文件服务
 *
 * @author hhwy
 */
@FeignClient(contextId = "remoteDemoService", value = "ft-service-pm", fallbackFactory = RemoteDemoFallbackFactory.class)
public interface RemoteDemoService {

    @GetMapping(value = "/pm/test")
    AjaxResult test(@RequestParam String code);
}
