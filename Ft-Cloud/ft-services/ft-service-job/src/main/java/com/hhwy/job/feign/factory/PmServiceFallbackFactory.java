package com.hhwy.job.feign.factory;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.job.feign.service.PmServiceApi;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class PmServiceFallbackFactory implements FallbackFactory<PmServiceApi> {

    @Override
    public PmServiceApi create(Throwable cause) {
        return new PmServiceApi() {
            @Override
            public AjaxResult workGroupSetUpWarn() {
                return null;
            }

            @Override
            public AjaxResult summarySetUpWarn() {
                return null;
            }

            @Override
            public AjaxResult evaluationSetUpWarn() {
                return null;
            }

            @Override
            public AjaxResult workPlanCommitWarn() {
                return null;
            }

            @Override
            public AjaxResult workPlanApprovalWarn() {
                return null;
            }
        };
    }
}
