package com.hhwy.flowable.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 文件服务
 *
 * @author hhwy
 */
@FeignClient(name = "ft-service-pm")
public interface PmServiceApi {


    /**
     *  修改合同流程状态
     */
    @RequestMapping(value = "/xmslContractInfo/listener/", method = RequestMethod.POST)
    AjaxResult updateContract(@RequestParam("id") Long id);

    /**
     * 修改前期策划工作小组流程状态
     * @param id
     * @return
     */
    @PostMapping("/qqchWorkGroup/listener")
    AjaxResult updateWorkGroupProcess(@RequestParam("id") Long id);
}
