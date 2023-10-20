package com.hhwy.job.feign.factory;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.job.feign.service.PmServiceApi;
import com.hhwy.job.feign.service.SystemServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class SystemServiceFallbackFactory implements FallbackFactory<SystemServiceApi> {

    private static final Logger log = LoggerFactory.getLogger(SystemServiceFallbackFactory.class);

    @Override
    public SystemServiceApi create(Throwable cause) {
        return new SystemServiceApi() {

            @Override
            public AjaxResult pullContry() {
                return AjaxResult.error();
            }

            @Override
            public AjaxResult pullCurrency() {
                return AjaxResult.error();
            }

            @Override
            public AjaxResult pullPeriodCurrency() {
                return AjaxResult.error();
            }
        };
    }
}
