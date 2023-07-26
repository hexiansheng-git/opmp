package com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyPersonPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.service.IQqchSurveyPersonPlanService;
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
 * @date 2023-07-25 11:09:33
 * @remark 2.1.3 勘察设计队伍配置 ---人员策划
 */
@Validated
@RestController
@RequestMapping("/qqchSurveyPersonPlan")
public class QqchSurveyPersonPlanController extends BaseController {

    @Autowired
    private IQqchSurveyPersonPlanService qqchSurveyPersonPlanService;


    @PreAuthorize(hasPermi = "qqchSurveyPersonPlan:list")
    @GetMapping
    public AjaxResult getQqchSurveyPersonPlan(@Validated(ValidationGroups.Get.class) QqchSurveyPersonPlan qqchSurveyPersonPlanParam) {
        QqchSurveyPersonPlan qqchSurveyPersonPlan = qqchSurveyPersonPlanService.getQqchSurveyPersonPlan(qqchSurveyPersonPlanParam);
        return AjaxResult.success(qqchSurveyPersonPlan);
    }

    @PreAuthorize(hasPermi = "qqchSurveyPersonPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchSurveyPersonPlanList(@Validated(ValidationGroups.Select.class) QqchSurveyPersonPlan qqchSurveyPersonPlanParam) {
        startPage();
        List<QqchSurveyPersonPlan> qqchSurveyPersonPlanList = qqchSurveyPersonPlanService.getQqchSurveyPersonPlanList(qqchSurveyPersonPlanParam);
        return getDataTableAjaxResult(qqchSurveyPersonPlanList);
    }

    @PreAuthorize(hasPermi = "qqchSurveyPersonPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSurveyPersonPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyPersonPlan qqchSurveyPersonPlanParam) {
        qqchSurveyPersonPlanService.insertQqchSurveyPersonPlan(qqchSurveyPersonPlanParam);
        return AjaxResult.success(qqchSurveyPersonPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchSurveyPersonPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSurveyPersonPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSurveyPersonPlan> qqchSurveyPersonPlanListParam) {
        qqchSurveyPersonPlanService.insertQqchSurveyPersonPlanList(qqchSurveyPersonPlanListParam);
        return AjaxResult.success(qqchSurveyPersonPlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchSurveyPersonPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSurveyPersonPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchSurveyPersonPlan qqchSurveyPersonPlanParam) {
        return toAjax(qqchSurveyPersonPlanService.updateQqchSurveyPersonPlan(qqchSurveyPersonPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyPersonPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSurveyPersonPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSurveyPersonPlan> qqchSurveyPersonPlanListParam) {
        return toAjax(qqchSurveyPersonPlanService.updateQqchSurveyPersonPlanList(qqchSurveyPersonPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyPersonPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSurveyPersonPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSurveyPersonPlan qqchSurveyPersonPlanParam) {
        return toAjax(qqchSurveyPersonPlanService.deleteQqchSurveyPersonPlan(qqchSurveyPersonPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyPersonPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSurveyPersonPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchSurveyPersonPlanPkList = Arrays.asList(ids);
        return toAjax(qqchSurveyPersonPlanService.deleteQqchSurveyPersonPlanByPks(qqchSurveyPersonPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSurveyPersonPlan qqchSurveyPersonPlanParam) throws IOException {
        List<QqchSurveyPersonPlan> qqchSurveyPersonPlanList = qqchSurveyPersonPlanService.getQqchSurveyPersonPlanList(qqchSurveyPersonPlanParam);
        ExcelUtils<QqchSurveyPersonPlan> util = new ExcelUtils<>(QqchSurveyPersonPlan.class);
        util.exportExcel(response, qqchSurveyPersonPlanList, DateUtils.getDate());
    }
}
