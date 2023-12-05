package com.hhwy.job.feign.factory;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.job.feign.service.PmServiceApi;
import feign.Request;
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
            public AjaxResult summaryWarn() {
                return null;
            }

            @Override
            public AjaxResult evaluationWarn() {
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

            @Override
            public AjaxResult personControlPlanWarn() {
                return null;
            }

            @Override
            public AjaxResult initDiffAnalysis() {
                return null;
            }

            @Override
            public AjaxResult initJdglData4P6ByAll(Request.Options options) {
                return null;
            }

            @Override
            public AjaxResult weekTimerTrack() {
                return null;
            }

            @Override
            public AjaxResult preparationFirstStageWarn() {
                return null;
            }

            @Override
            public AjaxResult preparationSecondStageWarn() {
                return null;
            }

            @Override
            public AjaxResult preparationThirdStageWarn() {
                return null;
            }

            @Override
            public AjaxResult reviewWarn() {
                return null;
            }
        };
    }
}
