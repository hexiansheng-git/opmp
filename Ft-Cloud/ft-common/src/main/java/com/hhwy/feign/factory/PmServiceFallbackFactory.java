package com.hhwy.feign.factory;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.feign.service.domain.QqchMeasureExpRange;
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
            public AjaxResult qqchMeasureExpRangeList(QqchMeasureExpRange dto) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }
        };
    }
}
