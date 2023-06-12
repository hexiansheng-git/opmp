package com.hhwy.demo.api;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.demo.api.factory.RemoteDemoFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 文件服务
 *
 * @author hhwy
 */
@FeignClient(contextId = "remoteDemoService", value = "ft-service-demo", fallbackFactory = RemoteDemoFallbackFactory.class)
public interface RemoteDemoService {

    @GetMapping(value = "/demo/test")
    AjaxResult test(@RequestParam String code);
}
