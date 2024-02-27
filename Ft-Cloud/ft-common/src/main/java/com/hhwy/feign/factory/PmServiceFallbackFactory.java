package com.hhwy.feign.factory;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.domain.CommonQqchMeasureExpRange;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.dto.DesignDisclosurePlanDto;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Demo服务降级处理
 *
 * @author hhwy
 */
@Component
public class PmServiceFallbackFactory implements FallbackFactory<PmServiceApi> {
    private static final Logger log = LoggerFactory.getLogger(PmServiceFallbackFactory.class);

    @Override
    public PmServiceApi create(Throwable throwable) {
        log.error("项目服务调用失败:{}", throwable.getMessage());
        return new PmServiceApi() {
            @Override
            public AjaxResult insertProjectTenant(Map map) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public AjaxResult qqchMeasureExpRangeList(CommonQqchMeasureExpRange dto) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public List<QqchPostSetting> getTechDeptList() {
                return null;
            }

            @Override
            public AjaxResult insertSyncLog(SysSyncInfoLog log) {
                return AjaxResult.error("请求失败:", throwable.getMessage());
            }
            @Override
            public Map<String, Object> getPrjInfo() {
                return null;
            }

            @Override
            public AjaxResult projectInfo() {
                return AjaxResult.error("请求失败:", throwable.getMessage());
            }

            @Override
            public AjaxResult qqchMeasureExpPlanList() {
                return AjaxResult.error("请求失败:", throwable.getMessage());
            }

            @Override
            public AjaxResult feignPlanList() {
                return AjaxResult.error("请求失败:", throwable.getMessage());
            }

            @Override
            public AjaxResult qqchMeasureExpEquList() {
                return AjaxResult.error("请求失败:", throwable.getMessage());
            }

            @Override
            public AjaxResult feignExperimentList() {
                return AjaxResult.error("请求失败:", throwable.getMessage());
            }

            @Override
            public AjaxResult getQqchSurveyOrganizationList() {
                return AjaxResult.error("请求失败:", throwable.getMessage());
            }

            @Override
            public AjaxResult getData() {
                return AjaxResult.error("请求失败:", throwable.getMessage());
            }

            @Override
            public AjaxResult getqqchSurveyDesignTeams() {
                return AjaxResult.error("请求失败:", throwable.getMessage());
            }

            @Override
            public List<DesignDisclosurePlanDto> getDisclosurePlanDtoList() {
                return null;
            }
        };
    }
}
