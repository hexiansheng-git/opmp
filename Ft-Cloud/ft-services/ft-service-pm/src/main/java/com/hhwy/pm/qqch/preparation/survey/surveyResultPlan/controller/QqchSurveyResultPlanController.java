package com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.domain.QqchSurveyResultPlan;
import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.domain.QqchSurveyResultPlanVo;
import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.service.IQqchSurveyResultPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author ldd
 * @date 2023-07-21 16:45:04
 * @remark 2.3.1 勘测成果清单及计划
 */
@Validated
@RestController
@RequestMapping("/qqchSurveyResultPlan")
public class QqchSurveyResultPlanController extends BaseController {

    @Autowired
    private IQqchSurveyResultPlanService qqchSurveyResultPlanService;


    /**
     *  列表查询
     *
     * @param qqchSurveyResultPlanParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyResultPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchSurveyResultPlanList(@Validated(ValidationGroups.Select.class) QqchSurveyResultPlan qqchSurveyResultPlanParam) {
        QqchSurveyResultPlanVo vo = qqchSurveyResultPlanService.getQqchSurveyResultPlanList(qqchSurveyResultPlanParam);
        return AjaxResult.success(vo);
    }

    /**
     *  批量新增
     * @param qqchSurveyResultPlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyResultPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSurveyResultPlanList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyResultPlanVo qqchSurveyResultPlanVo) {
        qqchSurveyResultPlanService.save(qqchSurveyResultPlanVo);
        return AjaxResult.success(qqchSurveyResultPlanVo);
    }

    /**
     *  批量新增 修改
     * @param qqchSurveyResultPlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyResultPlan:confirm")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyResultPlanVo qqchSurveyResultPlanVo) {
        qqchSurveyResultPlanService.confirm(qqchSurveyResultPlanVo);
        return AjaxResult.success(qqchSurveyResultPlanVo);
    }


}
