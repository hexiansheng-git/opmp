package com.hhwy.pm.qqch.preparation.survey.risk.controller;

import com.hhwy.pm.qqch.preparation.survey.risk.domain.vo.QqchDailyControlPlanVo;
import com.hhwy.pm.qqch.preparation.survey.risk.service.IQqchDailyControlPlanService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-07-13 11:39:57
 * @remark 日常管控策划
 */
@Validated
@RestController
@RequestMapping("/qqchDailyControlPlan")
public class QqchDailyControlPlanController extends BaseController {

    @Autowired
    private IQqchDailyControlPlanService qqchDailyControlPlanService;

    /**
     * 台账
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getQqchDailyControlPlanList(BigDecimal version) {
        QqchDailyControlPlanVo qqchDailyControlPlanVo = qqchDailyControlPlanService.getQqchDailyControlPlanVo(version);
        return AjaxResult.success(qqchDailyControlPlanVo);
    }

    /**
     * 保存
     * @param qqchDailyControlPlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDailyControlPlan:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Update.class) @RequestBody QqchDailyControlPlanVo qqchDailyControlPlanVo) {
        qqchDailyControlPlanService.save(qqchDailyControlPlanVo);
        return AjaxResult.success();
    }

    /**
     * 确认
     * @param qqchDailyControlPlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDailyControlPlan:save")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchDailyControlPlanVo qqchDailyControlPlanVo) {
        qqchDailyControlPlanService.confirm(qqchDailyControlPlanVo);
        return AjaxResult.success();
    }
}
