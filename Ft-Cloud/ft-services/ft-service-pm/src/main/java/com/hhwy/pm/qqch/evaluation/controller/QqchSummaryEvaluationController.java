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
}
