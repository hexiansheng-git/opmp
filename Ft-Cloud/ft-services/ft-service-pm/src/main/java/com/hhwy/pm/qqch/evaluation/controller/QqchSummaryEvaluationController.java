package com.hhwy.pm.qqch.evaluation.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.evaluation.domain.QqchSummaryEvaluation;
import com.hhwy.pm.qqch.evaluation.service.IQqchSummaryEvaluationService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-07-24 16:48:10
 * @remark 前期策划总结评价
 */
@Validated
@RestController
@RequestMapping("/qqchSummaryEvaluation")
public class QqchSummaryEvaluationController extends BaseController {

    @Autowired
    private IQqchSummaryEvaluationService qqchSummaryEvaluationService;

    @PreAuthorize(hasPermi = "qqchSummaryEvaluation:list")
    @GetMapping("/getQqchSummaryEvaluation")
    public AjaxResult getQqchSummaryEvaluation(
        @Validated(ValidationGroups.Get.class) QqchSummaryEvaluation qqchSummaryEvaluationParam) {
        QqchSummaryEvaluation qqchSummaryEvaluation = qqchSummaryEvaluationService
            .getQqchSummaryEvaluation(qqchSummaryEvaluationParam);
        return AjaxResult.success(qqchSummaryEvaluation);
    }

    @PreAuthorize(hasPermi = "qqchSummaryEvaluation:add")
    @PostMapping("/save")
    public AjaxResult save(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchSummaryEvaluation qqchSummaryEvaluationParam) {
        qqchSummaryEvaluationService.save(qqchSummaryEvaluationParam);
        return AjaxResult.success(qqchSummaryEvaluationParam);
    }

    /**
     * 提交
     *
     * @param qqchSummaryEvaluationParam
     * @return
     */
    @PostMapping("/submit")
    public AjaxResult submit(@Validated({ValidationGroups.Update.class,
        ValidationGroups.Save.class}) @RequestBody QqchSummaryEvaluation qqchSummaryEvaluationParam) {
        qqchSummaryEvaluationService.submit(qqchSummaryEvaluationParam);
        return AjaxResult.success(qqchSummaryEvaluationParam);
    }

    /**
     * 更新流程数据
     *
     * @param id 主键
     * @return 监听器
     */
    @RequestMapping(value = "/listener", method = RequestMethod.POST)
    public AjaxResult updateQqchSummaryEvaluationProcess(@RequestParam("id") Long id) {
        qqchSummaryEvaluationService.updateQqchSummaryEvaluationProcess(id);
        return AjaxResult.success("成功");
    }

    /**
     * 总结发送预警消息
     *
     * @return
     */
    @GetMapping("summarySetUpWarn")
    public AjaxResult summarySetUpWarn() {
        qqchSummaryEvaluationService.summaryEvaluationSetUpWarn("1");
        return AjaxResult.success();
    }

    /**
     * 评价发送预警消息
     *
     * @return
     */
    @GetMapping("evaluationSetUpWarn")
    public AjaxResult evaluationSetUpWarn() {
        qqchSummaryEvaluationService.summaryEvaluationSetUpWarn("2");
        return AjaxResult.success();
    }
}
