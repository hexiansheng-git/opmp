package com.hhwy.pm.qqch.preparation.survey.risk.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchDailyControlPlan;
import com.hhwy.pm.qqch.preparation.survey.risk.service.IQqchDailyControlPlanService;
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
 * @date 2023-07-13 11:39:57
 * @remark 日常管控策划
 */
@Validated
@RestController
@RequestMapping("/qqchDailyControlPlan")
public class QqchDailyControlPlanController extends BaseController {

    @Autowired
    private IQqchDailyControlPlanService qqchDailyControlPlanService;


    @PreAuthorize(hasPermi = "qqchDailyControlPlan:list")
    @GetMapping
    public AjaxResult getQqchDailyControlPlan(@Validated(ValidationGroups.Get.class) @RequestBody QqchDailyControlPlan qqchDailyControlPlanParam) {
        QqchDailyControlPlan qqchDailyControlPlan = qqchDailyControlPlanService.getQqchDailyControlPlan(qqchDailyControlPlanParam);
        return AjaxResult.success(qqchDailyControlPlan);
    }

    @PreAuthorize(hasPermi = "qqchDailyControlPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchDailyControlPlanList(@Validated(ValidationGroups.Select.class) @RequestBody QqchDailyControlPlan qqchDailyControlPlanParam) {
        startPage();
        List<QqchDailyControlPlan> qqchDailyControlPlanList = qqchDailyControlPlanService.getQqchDailyControlPlanList(qqchDailyControlPlanParam);
        return getDataTableAjaxResult(qqchDailyControlPlanList);
    }

    @PreAuthorize(hasPermi = "qqchDailyControlPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchDailyControlPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchDailyControlPlan qqchDailyControlPlanParam) {
        qqchDailyControlPlanService.insertQqchDailyControlPlan(qqchDailyControlPlanParam);
        return AjaxResult.success(qqchDailyControlPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchDailyControlPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchDailyControlPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchDailyControlPlan> qqchDailyControlPlanListParam) {
        qqchDailyControlPlanService.insertQqchDailyControlPlanList(qqchDailyControlPlanListParam);
        return AjaxResult.success(qqchDailyControlPlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchDailyControlPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchDailyControlPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchDailyControlPlan qqchDailyControlPlanParam) {
        return toAjax(qqchDailyControlPlanService.updateQqchDailyControlPlan(qqchDailyControlPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchDailyControlPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchDailyControlPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchDailyControlPlan> qqchDailyControlPlanListParam) {
        return toAjax(qqchDailyControlPlanService.updateQqchDailyControlPlanList(qqchDailyControlPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchDailyControlPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchDailyControlPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchDailyControlPlan qqchDailyControlPlanParam) {
        return toAjax(qqchDailyControlPlanService.deleteQqchDailyControlPlan(qqchDailyControlPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchDailyControlPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchDailyControlPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchDailyControlPlanPkList = Arrays.asList(ids);
        return toAjax(qqchDailyControlPlanService.deleteQqchDailyControlPlanByPks(qqchDailyControlPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchDailyControlPlan qqchDailyControlPlanParam) throws IOException {
        List<QqchDailyControlPlan> qqchDailyControlPlanList = qqchDailyControlPlanService.getQqchDailyControlPlanList(qqchDailyControlPlanParam);
        ExcelUtils<QqchDailyControlPlan> util = new ExcelUtils<>(QqchDailyControlPlan.class);
        util.exportExcel(response, qqchDailyControlPlanList, DateUtils.getDate());
    }
}
