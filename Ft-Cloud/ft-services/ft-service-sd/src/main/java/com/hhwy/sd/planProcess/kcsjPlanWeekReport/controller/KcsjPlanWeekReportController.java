package com.hhwy.sd.planProcess.kcsjPlanWeekReport.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sd.planProcess.kcsjPlanWeekReport.service.IKcsjPlanWeekReportService;
import com.hhwy.sd.planProcess.kcsjPlanWeekReport.domain.KcsjPlanWeekReport;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2023-12-18 11:12:15
 * @remark
 */
@Validated
@RestController
@RequestMapping("/kcsjPlanWeekReport")
public class KcsjPlanWeekReportController extends BaseController {

    @Autowired
    private IKcsjPlanWeekReportService kcsjPlanWeekReportService;


    @PreAuthorize(hasPermi = "kcsjPlanWeekReport:list")
    @GetMapping
    public AjaxResult getKcsjPlanWeekReport(@Validated(ValidationGroups.Get.class) KcsjPlanWeekReport kcsjPlanWeekReportParam) {
        KcsjPlanWeekReport kcsjPlanWeekReport = kcsjPlanWeekReportService.getKcsjPlanWeekReport(kcsjPlanWeekReportParam);
        return AjaxResult.success(kcsjPlanWeekReport);
    }

    @PreAuthorize(hasPermi = "kcsjPlanWeekReport:list")
    @GetMapping("/list")
    public AjaxResult getKcsjPlanWeekReportList(@Validated(ValidationGroups.Select.class) KcsjPlanWeekReport kcsjPlanWeekReportParam) {
        startPage();
        List<KcsjPlanWeekReport> kcsjPlanWeekReportList = kcsjPlanWeekReportService.getKcsjPlanWeekReportList(kcsjPlanWeekReportParam);
        return getDataTableAjaxResult(kcsjPlanWeekReportList);
    }

    @PreAuthorize(hasPermi = "kcsjPlanWeekReport:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjPlanWeekReport(@Validated(ValidationGroups.Save.class) @RequestBody KcsjPlanWeekReport kcsjPlanWeekReportParam) {
        kcsjPlanWeekReportService.insertKcsjPlanWeekReport(kcsjPlanWeekReportParam);
        return AjaxResult.success(kcsjPlanWeekReportParam);
    }

    @PreAuthorize(hasPermi = "kcsjPlanWeekReport:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjPlanWeekReportList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjPlanWeekReport> kcsjPlanWeekReportListParam) {
        kcsjPlanWeekReportService.insertKcsjPlanWeekReportList(kcsjPlanWeekReportListParam);
        return AjaxResult.success(kcsjPlanWeekReportListParam);
    }

    @PreAuthorize(hasPermi = "kcsjPlanWeekReport:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjPlanWeekReport(@Validated(ValidationGroups.Update.class) @RequestBody KcsjPlanWeekReport kcsjPlanWeekReportParam) {
        return toAjax(kcsjPlanWeekReportService.updateKcsjPlanWeekReport(kcsjPlanWeekReportParam));
    }

    @PreAuthorize(hasPermi = "kcsjPlanWeekReport:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjPlanWeekReportList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjPlanWeekReport> kcsjPlanWeekReportListParam) {
        return toAjax(kcsjPlanWeekReportService.updateKcsjPlanWeekReportList(kcsjPlanWeekReportListParam));
    }

    @PreAuthorize(hasPermi = "kcsjPlanWeekReport:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjPlanWeekReport(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjPlanWeekReport kcsjPlanWeekReportParam) {
        return toAjax(kcsjPlanWeekReportService.deleteKcsjPlanWeekReport(kcsjPlanWeekReportParam));
    }

    @PreAuthorize(hasPermi = "kcsjPlanWeekReport:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjPlanWeekReportByPks(@PathVariable Long[] ids) {
        List<Long> kcsjPlanWeekReportPkList = Arrays.asList(ids);
        return toAjax(kcsjPlanWeekReportService.deleteKcsjPlanWeekReportByPks(kcsjPlanWeekReportPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjPlanWeekReport kcsjPlanWeekReportParam) throws IOException {
        List<KcsjPlanWeekReport> kcsjPlanWeekReportList = kcsjPlanWeekReportService.getKcsjPlanWeekReportList(kcsjPlanWeekReportParam);
        ExcelUtils<KcsjPlanWeekReport> util = new ExcelUtils<>(KcsjPlanWeekReport.class);
        util.exportExcel(response, kcsjPlanWeekReportList, DateUtils.getDate());
    }
}
