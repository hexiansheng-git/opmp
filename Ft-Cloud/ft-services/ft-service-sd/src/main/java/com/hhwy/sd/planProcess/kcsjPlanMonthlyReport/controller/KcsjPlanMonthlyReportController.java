package com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.domain.KcsjPlanMonthlyReport;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.service.IKcsjPlanMonthlyReportService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-12-18 11:21:39
 * @remark
 */
@Validated
@RestController
@RequestMapping("/kcsjPlanMonthlyReport")
public class KcsjPlanMonthlyReportController extends BaseController {

    @Autowired
    private IKcsjPlanMonthlyReportService kcsjPlanMonthlyReportService;


    @PreAuthorize(hasPermi = "kcsjPlanMonthlyReport:list")
    @GetMapping
    public AjaxResult getKcsjPlanMonthlyReport(@Validated(ValidationGroups.Get.class) KcsjPlanMonthlyReport kcsjPlanMonthlyReportParam) {
        KcsjPlanMonthlyReport kcsjPlanMonthlyReport = kcsjPlanMonthlyReportService.getKcsjPlanMonthlyReport(kcsjPlanMonthlyReportParam);
        return AjaxResult.success(kcsjPlanMonthlyReport);
    }

    @PreAuthorize(hasPermi = "kcsjPlanMonthlyReport:list")
    @GetMapping("/list")
    public AjaxResult getKcsjPlanMonthlyReportList(@Validated(ValidationGroups.Select.class) KcsjPlanMonthlyReport kcsjPlanMonthlyReportParam) {
        startPage();
        List<KcsjPlanMonthlyReport> kcsjPlanMonthlyReportList = kcsjPlanMonthlyReportService.getKcsjPlanMonthlyReportList(kcsjPlanMonthlyReportParam);
        return getDataTableAjaxResult(kcsjPlanMonthlyReportList);
    }

    @PreAuthorize(hasPermi = "kcsjPlanMonthlyReport:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjPlanMonthlyReport(@Validated(ValidationGroups.Save.class) @RequestBody KcsjPlanMonthlyReport kcsjPlanMonthlyReportParam) {
        kcsjPlanMonthlyReportService.insertKcsjPlanMonthlyReport(kcsjPlanMonthlyReportParam);
        return AjaxResult.success(kcsjPlanMonthlyReportParam);
    }

    @PreAuthorize(hasPermi = "kcsjPlanMonthlyReport:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjPlanMonthlyReportList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjPlanMonthlyReport> kcsjPlanMonthlyReportListParam) {
        kcsjPlanMonthlyReportService.insertKcsjPlanMonthlyReportList(kcsjPlanMonthlyReportListParam);
        return AjaxResult.success(kcsjPlanMonthlyReportListParam);
    }

    @PreAuthorize(hasPermi = "kcsjPlanMonthlyReport:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjPlanMonthlyReport(@Validated(ValidationGroups.Update.class) @RequestBody KcsjPlanMonthlyReport kcsjPlanMonthlyReportParam) {
        return toAjax(kcsjPlanMonthlyReportService.updateKcsjPlanMonthlyReport(kcsjPlanMonthlyReportParam));
    }

    @PreAuthorize(hasPermi = "kcsjPlanMonthlyReport:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjPlanMonthlyReportList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjPlanMonthlyReport> kcsjPlanMonthlyReportListParam) {
        return toAjax(kcsjPlanMonthlyReportService.updateKcsjPlanMonthlyReportList(kcsjPlanMonthlyReportListParam));
    }

    @PreAuthorize(hasPermi = "kcsjPlanMonthlyReport:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjPlanMonthlyReport(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjPlanMonthlyReport kcsjPlanMonthlyReportParam) {
        return toAjax(kcsjPlanMonthlyReportService.deleteKcsjPlanMonthlyReport(kcsjPlanMonthlyReportParam));
    }

    @PreAuthorize(hasPermi = "kcsjPlanMonthlyReport:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjPlanMonthlyReportByPks(@PathVariable Long[] ids) {
        List<Long> kcsjPlanMonthlyReportPkList = Arrays.asList(ids);
        return toAjax(kcsjPlanMonthlyReportService.deleteKcsjPlanMonthlyReportByPks(kcsjPlanMonthlyReportPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjPlanMonthlyReport kcsjPlanMonthlyReportParam) throws IOException {
        List<KcsjPlanMonthlyReport> kcsjPlanMonthlyReportList = kcsjPlanMonthlyReportService.getKcsjPlanMonthlyReportList(kcsjPlanMonthlyReportParam);
        ExcelUtils<KcsjPlanMonthlyReport> util = new ExcelUtils<>(KcsjPlanMonthlyReport.class);
        util.exportExcel(response, kcsjPlanMonthlyReportList, DateUtils.getDate());
    }
}
