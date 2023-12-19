package com.hhwy.job.feign.factory;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.job.feign.service.SdServiceApi;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class SdServiceFallbackFactory implements FallbackFactory<SdServiceApi> {

    @Override
    public SdServiceApi create(Throwable cause) {
        return new SdServiceApi() {

            @Override
            public AjaxResult generateMonthlyReport() {
                return null;
            }

            @Override
            public AjaxResult produceData() {
                return null;
            }
        };
    }
}
