package com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.domain.QqchSurveyResultPlan;
import com.hhwy.pm.qqch.preparation.survey.surveyResultPlan.service.IQqchSurveyResultPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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


    @PreAuthorize(hasPermi = "qqchSurveyResultPlan:list")
    @GetMapping
    public AjaxResult getQqchSurveyResultPlan(@Validated(ValidationGroups.Get.class) QqchSurveyResultPlan qqchSurveyResultPlanParam) {
        QqchSurveyResultPlan qqchSurveyResultPlan = qqchSurveyResultPlanService.getQqchSurveyResultPlan(qqchSurveyResultPlanParam);
        return AjaxResult.success(qqchSurveyResultPlan);
    }

    @PreAuthorize(hasPermi = "qqchSurveyResultPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchSurveyResultPlanList(@Validated(ValidationGroups.Select.class) QqchSurveyResultPlan qqchSurveyResultPlanParam) {
        startPage();
        List<QqchSurveyResultPlan> qqchSurveyResultPlanList = qqchSurveyResultPlanService.getQqchSurveyResultPlanList(qqchSurveyResultPlanParam);
        return getDataTableAjaxResult(qqchSurveyResultPlanList);
    }

    @PreAuthorize(hasPermi = "qqchSurveyResultPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSurveyResultPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyResultPlan qqchSurveyResultPlanParam) {
        qqchSurveyResultPlanService.insertQqchSurveyResultPlan(qqchSurveyResultPlanParam);
        return AjaxResult.success(qqchSurveyResultPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchSurveyResultPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSurveyResultPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSurveyResultPlan> qqchSurveyResultPlanListParam) {
        qqchSurveyResultPlanService.insertQqchSurveyResultPlanList(qqchSurveyResultPlanListParam);
        return AjaxResult.success(qqchSurveyResultPlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchSurveyResultPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSurveyResultPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchSurveyResultPlan qqchSurveyResultPlanParam) {
        return toAjax(qqchSurveyResultPlanService.updateQqchSurveyResultPlan(qqchSurveyResultPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyResultPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSurveyResultPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSurveyResultPlan> qqchSurveyResultPlanListParam) {
        return toAjax(qqchSurveyResultPlanService.updateQqchSurveyResultPlanList(qqchSurveyResultPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyResultPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSurveyResultPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSurveyResultPlan qqchSurveyResultPlanParam) {
        return toAjax(qqchSurveyResultPlanService.deleteQqchSurveyResultPlan(qqchSurveyResultPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyResultPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSurveyResultPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchSurveyResultPlanPkList = Arrays.asList(ids);
        return toAjax(qqchSurveyResultPlanService.deleteQqchSurveyResultPlanByPks(qqchSurveyResultPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSurveyResultPlan qqchSurveyResultPlanParam) throws IOException {
        List<QqchSurveyResultPlan> qqchSurveyResultPlanList = qqchSurveyResultPlanService.getQqchSurveyResultPlanList(qqchSurveyResultPlanParam);
        ExcelUtils<QqchSurveyResultPlan> util = new ExcelUtils<>(QqchSurveyResultPlan.class);
        util.exportExcel(response, qqchSurveyResultPlanList, DateUtils.getDate());
    }
}
