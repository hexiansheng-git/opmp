package com.hhwy.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.factory.PmServiceFallbackFactory;
import com.hhwy.feign.factory.SystemServiceFallbackFactory;
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
}
