package com.hhwy.pm.qqch.preparation.technique.scheme.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionReviewPlanVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionReviewPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-07-17 15:32:17
 * @remark 3.4.5施工方案编审计划
 */
@Validated
@RestController
@RequestMapping("/qqchConstructionReviewPlan")
public class QqchConstructionReviewPlanController extends BaseController {

    @Autowired
    private IQqchConstructionReviewPlanService qqchConstructionReviewPlanService;

    @PreAuthorize(hasPermi = "qqchConstructionReviewPlan:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo = qqchConstructionReviewPlanService
            .getQqchConstructionReviewPlanList(version);
        return AjaxResult.success(qqchConstructionReviewPlanVo);
    }

    @PreAuthorize(hasPermi = "qqchDangerConstructionList:add")
    @PostMapping("/syncData")
    public AjaxResult syncData(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo) {
        qqchConstructionReviewPlanService.syncData(qqchConstructionReviewPlanVo);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchConstructionReviewPlan:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo) {
        qqchConstructionReviewPlanService.batchSave(qqchConstructionReviewPlanVo);
        return AjaxResult.success();
    }
}
