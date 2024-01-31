package com.hhwy.flowable.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 施工技术
 *
 * @author hhwy
 */
@FeignClient(name = "ft-service-sp")
public interface SpServiceApi {

    /**
     * 修改四新成果管理
     */
    @RequestMapping(value = "/sgjsFourNewsAchievement/listener", method = RequestMethod.POST)
    AjaxResult updateFourNewsAchievement(@RequestParam("id") Long id, @RequestParam("isPass") String isPass);

    /**
     * 修改工艺工法管理
     */
    @RequestMapping(value = "/sgjsTechMethod/listener", method = RequestMethod.POST)
    AjaxResult updateSgjsTechMethod(@RequestParam("id") Long id, @RequestParam("isPass") String isPass);

}
