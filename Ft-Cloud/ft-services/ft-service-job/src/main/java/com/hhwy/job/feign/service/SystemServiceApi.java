package com.hhwy.job.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 文件服务
 *
 * @author hhwy
 */
@FeignClient(name = "ft-service-system")
public interface SystemServiceApi {

    /**
     *  从主数据同步国别数据
     *
     * @return
     */
    @GetMapping("/pullContry")
    AjaxResult pullContry();

    /**
     * 从主数据同步币种数据
     *
     * @return
     */
    @GetMapping("/pullCurrency")
    AjaxResult pullCurrency();

    /**
     * 从主数据同步期次和期次汇率数据
     *
     * @return
     */
    @GetMapping("/pullPeriodCurrency")
    AjaxResult pullPeriodCurrency();

}
