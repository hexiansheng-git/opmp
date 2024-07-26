package com.hhwy.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.factory.FlowServiceFallbackFactory;
import com.hhwy.feign.factory.PmServiceFallbackFactory;
import org.apache.ibatis.annotations.Param;
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
@FeignClient(name = "ft-service-flowable", fallbackFactory = FlowServiceFallbackFactory.class)
public interface FlowServiceApi {


    /**
     *  拆入对应租户的项目信息
     */
    @PostMapping("/myFlow/isNowfirstNode")
    AjaxResult isNowfirstNode(@RequestParam("insId") String insId);

    /**
     *  获取任务信息
     */
    @PostMapping("/myFlow/taskInfoDetail")
    AjaxResult taskInfoDetail(@RequestParam("taskId") String taskId);

    /**
     *  流程处理列表
     */
    @GetMapping("/bpmn/handleList")
    AjaxResult handleList(@RequestParam("businessKey") String businessKey,@RequestParam("tableName") String tableName);
}
