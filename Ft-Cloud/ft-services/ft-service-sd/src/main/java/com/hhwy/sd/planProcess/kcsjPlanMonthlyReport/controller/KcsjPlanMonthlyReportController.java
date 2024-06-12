package com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.domain.KcsjPlanMonthlyReport;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.domain.vo.PlanMonthlyReportQueryVo;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.service.IKcsjPlanMonthlyReportService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author han
 * @date 2023-12-18 11:21:39
 * @remark 勘察设计管理-计划进度管理-月报
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

    /**
     * 台账
     * @param queryVo
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjPlanMonthlyReport:list")
    @GetMapping("/list")
    public AjaxResult getKcsjPlanMonthlyReportList(@Validated(ValidationGroups.Select.class) PlanMonthlyReportQueryVo queryVo) {
        startPage();
        List<KcsjPlanMonthlyReport> kcsjPlanMonthlyReportList = kcsjPlanMonthlyReportService.getKcsjPlanMonthlyReportList(queryVo);
        return getDataTableAjaxResult(kcsjPlanMonthlyReportList);
    }

    @PreAuthorize(hasPermi = "kcsjPlanMonthlyReport:add")
    @PostMapping("/add")
    @CustomLogger(title = "勘察设计-勘察设计进度管理-月报",name = "月报",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertKcsjPlanMonthlyReport(@Validated(ValidationGroups.Save.class) @RequestBody KcsjPlanMonthlyReport kcsjPlanMonthlyReportParam) {
        kcsjPlanMonthlyReportService.insertKcsjPlanMonthlyReport(kcsjPlanMonthlyReportParam);
        return AjaxResult.success(kcsjPlanMonthlyReportParam);
    }

    @PreAuthorize(hasPermi = "kcsjPlanMonthlyReport:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "勘察设计-勘察设计进度管理-月报",name = "月报",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertKcsjPlanMonthlyReportList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjPlanMonthlyReport> kcsjPlanMonthlyReportListParam) {
        kcsjPlanMonthlyReportService.insertKcsjPlanMonthlyReportList(kcsjPlanMonthlyReportListParam);
        return AjaxResult.success(kcsjPlanMonthlyReportListParam);
    }

    @PreAuthorize(hasPermi = "kcsjPlanMonthlyReport:update")
    @PostMapping("/update")
    @CustomLogger(title = "勘察设计-勘察设计进度管理-月报",name = "月报",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateKcsjPlanMonthlyReport(@Validated(ValidationGroups.Update.class) @RequestBody KcsjPlanMonthlyReport kcsjPlanMonthlyReportParam) {
        return toAjax(kcsjPlanMonthlyReportService.updateKcsjPlanMonthlyReport(kcsjPlanMonthlyReportParam));
    }

    /**
     * 修改保存
     * @param kcsjPlanMonthlyReportList
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjPlanMonthlyReport:update")
    @PostMapping("/batchUpdate")
    @CustomLogger(title = "勘察设计-勘察设计进度管理-月报",name = "月报",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateKcsjPlanMonthlyReportList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjPlanMonthlyReport> kcsjPlanMonthlyReportList) {
        return toAjax(kcsjPlanMonthlyReportService.updateKcsjPlanMonthlyReportList(kcsjPlanMonthlyReportList));
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

    /**
     * 生成月报
     * @return
     */
    @GetMapping("/generateMonthlyReport")
    public AjaxResult generateMonthlyReport(){
        kcsjPlanMonthlyReportService.generateMonthlyReport();
        return AjaxResult.success();
    }

    @PostMapping("generateMonthlyReportByDate")
    public AjaxResult generateMonthlyReportByDate(Date date){
        kcsjPlanMonthlyReportService.generateMonthlyReportByDate(date);
        return AjaxResult.success();
    }
}
