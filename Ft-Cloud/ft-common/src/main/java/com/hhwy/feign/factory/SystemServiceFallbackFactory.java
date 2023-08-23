package com.hhwy.feign.factory;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.system.api.domain.SysTenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo服务降级处理
 *
 * @author hhwy
 */
@Component
public class SystemServiceFallbackFactory implements FallbackFactory<SystemServiceApi> {
    private static final Logger log = LoggerFactory.getLogger(SystemServiceFallbackFactory.class);

    @Override
    public SystemServiceApi create(Throwable throwable) {
        log.error("系统服务调用失败:{}", throwable.getMessage());
        return new SystemServiceApi() {
            @Override
            public AjaxResult resolveDict(String dictType, String dictValue) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public AjaxResult reverseDict(String dictType, String dictLabel) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public AjaxResult dictType(String dictType) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public AjaxResult getQqchMenu(String name) {
                return null;
            }

            @Override
            public List<SysTenant> tenantList() {
                return new ArrayList<>();
            }
        };
    }
}
