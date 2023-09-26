package com.hhwy.feign.factory;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.service.FlowServiceApi;
import com.hhwy.feign.service.PmServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Demo服务降级处理
 *
 * @author hhwy
 */
@Component
public class FlowServiceFallbackFactory implements FallbackFactory<FlowServiceApi> {
    private static final Logger log = LoggerFactory.getLogger(FlowServiceFallbackFactory.class);

    @Override
    public FlowServiceApi create(Throwable throwable) {
        log.error("流程服务调用失败:{}", throwable.getMessage());
        return new FlowServiceApi() {
            @Override
            public AjaxResult isNowfirstNode(String insId) {
                return AjaxResult.error("");
            }
        };
    }
}
