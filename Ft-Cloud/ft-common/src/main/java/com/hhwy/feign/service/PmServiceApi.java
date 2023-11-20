package com.hhwy.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.factory.PmServiceFallbackFactory;
import com.hhwy.feign.factory.SystemServiceFallbackFactory;
import com.hhwy.feign.service.domain.QqchMeasureExpRange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 文件服务
 *
 * @author hhwy
 */
@FeignClient(name = "ft-service-pm", fallbackFactory = PmServiceFallbackFactory.class)
public interface PmServiceApi {


    /**
     *  拆入对应租户的项目信息
     */
    @PostMapping("/projectBasicInfo/addTenant")
    AjaxResult insertProjectTenant(@RequestBody Map map);

    /**
     *  3.6.1测量工作概述
     */
    @GetMapping("/qqchMeasureExpRange/list")
    AjaxResult qqchMeasureExpRangeList(QqchMeasureExpRange dto);
}
