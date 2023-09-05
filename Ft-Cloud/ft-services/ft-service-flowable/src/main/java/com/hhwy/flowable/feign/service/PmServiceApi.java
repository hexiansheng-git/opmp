package com.hhwy.flowable.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
}
