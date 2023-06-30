package com.hhwy.feign.factory;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.api.RemoteDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Demo服务降级处理
 *
 * @author hhwy
 */
@Component
public class RemoteDemoFallbackFactory implements FallbackFactory<RemoteDemoService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteDemoFallbackFactory.class);

    @Override
    public RemoteDemoService create(Throwable throwable) {
        log.error("Demo服务调用失败:{}", throwable.getMessage());
        return new RemoteDemoService() {
            @Override
            public AjaxResult test(String code) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }
        };
    }
}
