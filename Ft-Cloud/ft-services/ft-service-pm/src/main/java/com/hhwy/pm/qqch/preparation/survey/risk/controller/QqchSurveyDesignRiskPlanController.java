package com.hhwy.pm.qqch.preparation.survey.risk.controller;

import com.hhwy.pm.qqch.preparation.survey.risk.domain.vo.QqchSurveyDesignRiskPlanVo;
import com.hhwy.pm.qqch.preparation.survey.risk.service.IQqchSurveyDesignRiskPlanService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author han
 * @date 2023-07-13 11:39:34
 * @remark 勘察设计风险策划
 */
@Validated
@RestController
@RequestMapping("/qqchSurveyDesignRiskPlan")
public class QqchSurveyDesignRiskPlanController extends BaseController {

    @Autowired
    private IQqchSurveyDesignRiskPlanService qqchSurveyDesignRiskPlanService;


    /**
     * 勘察设计风险策划Vo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchSurveyDesignRiskPlanVo() {
        QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo = qqchSurveyDesignRiskPlanService.getQqchSurveyDesignRiskPlanVo();
        return AjaxResult.success(qqchSurveyDesignRiskPlanVo);
    }

    /**
     * 保存
     * @param qqchSurveyDesignRiskPlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:update")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Update.class) @RequestBody QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo) {
        qqchSurveyDesignRiskPlanService.save(qqchSurveyDesignRiskPlanVo);
        return AjaxResult.success(qqchSurveyDesignRiskPlanVo);
    }

    /**
     * 确认
     * @param qqchSurveyDesignRiskPlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:update")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo) {
        qqchSurveyDesignRiskPlanService.confirm(qqchSurveyDesignRiskPlanVo);
        return AjaxResult.success(qqchSurveyDesignRiskPlanVo);
    }
}
