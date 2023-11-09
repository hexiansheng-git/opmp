package com.hhwy.job.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    @GetMapping("/qqchSummaryEvaluation/summaryWarn")
    AjaxResult summaryWarn();

    /**
     * 前期策划总结评价-评价预警消息
     *
     * @return
     */
    @GetMapping("/qqchSummaryEvaluation/evaluationWarn")
    AjaxResult evaluationWarn();

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

    /**
     * 前期策划编制第一阶段预警
     */
    @GetMapping("/qqchReview/preparationFirstStageWarn")
    AjaxResult preparationFirstStageWarn();

    /**
     * 前期策划编制第二阶段预警
     */
    @GetMapping("/qqchReview/preparationSecondStageWarn")
    AjaxResult preparationSecondStageWarn();

    /**
     * 前期策划编制第三阶段预警
     */
    @GetMapping("/qqchReview/preparationThirdStageWarn")
    AjaxResult preparationThirdStageWarn();

    /**
     * 前期策划评审预警
     */
    @GetMapping("/qqchReview/reviewWarn")
    AjaxResult reviewWarn();

    /**
     * 人员管控策划预警
     *
     * @return
     */
    @GetMapping("/qqchPersonControlPlan/personControlPlanWarn")
    AjaxResult personControlPlanWarn();


    /**
     * 进度计划差异化分析20号生成数据
     */
    @GetMapping("/jdglDiffAnalysis/initDiffAnalysis")
    AjaxResult initDiffAnalysis();

    /**
     * 总体进度计划凌晨更新P6数据
     */
    @PostMapping("/jdglData4P6/initJdglData4P6ByAll")
    AjaxResult initJdglData4P6ByAll();

    @PostMapping("/jdglProgressCorrectionTrack/weekTimerTrack")
    AjaxResult weekTimerTrack();
}
