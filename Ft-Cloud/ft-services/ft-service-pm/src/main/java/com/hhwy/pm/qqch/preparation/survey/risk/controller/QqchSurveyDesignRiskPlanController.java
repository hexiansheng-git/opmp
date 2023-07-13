package com.hhwy.pm.qqch.preparation.survey.risk.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchSurveyDesignRiskPlan;
import com.hhwy.pm.qqch.preparation.survey.risk.service.IQqchSurveyDesignRiskPlanService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
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


    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:list")
    @GetMapping
    public AjaxResult getQqchSurveyDesignRiskPlan(@Validated(ValidationGroups.Get.class) @RequestBody QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlanParam) {
        QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan = qqchSurveyDesignRiskPlanService.getQqchSurveyDesignRiskPlan(qqchSurveyDesignRiskPlanParam);
        return AjaxResult.success(qqchSurveyDesignRiskPlan);
    }

    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchSurveyDesignRiskPlanList(@Validated(ValidationGroups.Select.class) @RequestBody QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlanParam) {
        startPage();
        List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList = qqchSurveyDesignRiskPlanService.getQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanParam);
        return getDataTableAjaxResult(qqchSurveyDesignRiskPlanList);
    }

    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSurveyDesignRiskPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlanParam) {
        qqchSurveyDesignRiskPlanService.insertQqchSurveyDesignRiskPlan(qqchSurveyDesignRiskPlanParam);
        return AjaxResult.success(qqchSurveyDesignRiskPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSurveyDesignRiskPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanListParam) {
        qqchSurveyDesignRiskPlanService.insertQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanListParam);
        return AjaxResult.success(qqchSurveyDesignRiskPlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSurveyDesignRiskPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlanParam) {
        return toAjax(qqchSurveyDesignRiskPlanService.updateQqchSurveyDesignRiskPlan(qqchSurveyDesignRiskPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSurveyDesignRiskPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanListParam) {
        return toAjax(qqchSurveyDesignRiskPlanService.updateQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSurveyDesignRiskPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlanParam) {
        return toAjax(qqchSurveyDesignRiskPlanService.deleteQqchSurveyDesignRiskPlan(qqchSurveyDesignRiskPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSurveyDesignRiskPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchSurveyDesignRiskPlanPkList = Arrays.asList(ids);
        return toAjax(qqchSurveyDesignRiskPlanService.deleteQqchSurveyDesignRiskPlanByPks(qqchSurveyDesignRiskPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlanParam) throws IOException {
        List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList = qqchSurveyDesignRiskPlanService.getQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanParam);
        ExcelUtils<QqchSurveyDesignRiskPlan> util = new ExcelUtils<>(QqchSurveyDesignRiskPlan.class);
        util.exportExcel(response, qqchSurveyDesignRiskPlanList, DateUtils.getDate());
    }
}
