package com.hhwy.job.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 文件服务
 *
 * @author hhwy
 */
@FeignClient(name = "ft-service-pm")
public interface PmServiceApi {

    /**
     * 工作小组设立预警消息
     *
     * @return
     */
    @GetMapping("/qqchWorkGroup/workGroupSetUpWarn")
    AjaxResult workGroupSetUpWarn();

    /**
     * 前期策划总结评价-总结预警消息
     *
     * @return
     */
    @GetMapping("/qqchSummaryEvaluation/summarySetUpWarn")
    AjaxResult summarySetUpWarn();

    /**
     * 前期策划总结评价-评价预警消息
     *
     * @return
     */
    @GetMapping("/qqchSummaryEvaluation/evaluationSetUpWarn")
    AjaxResult evaluationSetUpWarn();

    /**
     * 工作计划提交预警
     *
     * @return
     */
    @GetMapping("/qqchWorkPlan/workPlanCommitWarn")
    AjaxResult workPlanCommitWarn();

    /**
     * 工作计划审批预警
     *
     * @return
     */
    @GetMapping("/qqchWorkPlan/workPlanApprovalWarn")
    AjaxResult workPlanApprovalWarn();
}
