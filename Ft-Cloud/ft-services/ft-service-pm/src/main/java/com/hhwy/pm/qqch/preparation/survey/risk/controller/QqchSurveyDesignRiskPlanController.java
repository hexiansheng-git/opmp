package com.hhwy.pm.qqch.preparation.survey.risk.controller;

import java.util.Arrays;
import java.util.List;
import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchSurveyDesignRiskPlan;
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
     * @param qqchSurveyDesignRiskPlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchSurveyDesignRiskPlanVo(@Validated(ValidationGroups.Select.class) @RequestBody QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlanParam) {
        QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo = qqchSurveyDesignRiskPlanService.getQqchSurveyDesignRiskPlanVo(qqchSurveyDesignRiskPlanParam);
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

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSurveyDesignRiskPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchSurveyDesignRiskPlanPkList = Arrays.asList(ids);
        return toAjax(qqchSurveyDesignRiskPlanService.deleteQqchSurveyDesignRiskPlanByPks(qqchSurveyDesignRiskPlanPkList));
    }
}
