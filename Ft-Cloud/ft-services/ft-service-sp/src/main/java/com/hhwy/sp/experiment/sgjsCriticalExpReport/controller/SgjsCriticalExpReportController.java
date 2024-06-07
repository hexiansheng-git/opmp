package com.hhwy.sp.experiment.sgjsCriticalExpReport.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.SgjsCriticalExpReport;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.vo.CriticalExpReportQueryVo;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.vo.CriticalExpReportVo;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.service.ISgjsCriticalExpReportService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-12-11 16:41:12
 * @remark 试验管理-关键试验报告
 */
@Validated
@RestController
@RequestMapping("/sgjsCriticalExpReport")
public class SgjsCriticalExpReportController extends BaseController {

    @Autowired
    private ISgjsCriticalExpReportService sgjsCriticalExpReportService;


    @PreAuthorize(hasPermi = "sgjsCriticalExpReport:list")
    @GetMapping
    public AjaxResult getSgjsCriticalExpReport(@Validated(ValidationGroups.Get.class) SgjsCriticalExpReport sgjsCriticalExpReportParam) {
        SgjsCriticalExpReport sgjsCriticalExpReport = sgjsCriticalExpReportService.getSgjsCriticalExpReport(sgjsCriticalExpReportParam);
        return AjaxResult.success(sgjsCriticalExpReport);
    }

    @PreAuthorize(hasPermi = "sgjsCriticalExpReport:list")
    @GetMapping("/list")
    public AjaxResult getSgjsCriticalExpReportList(@Validated(ValidationGroups.Select.class) CriticalExpReportQueryVo queryVo) {
        startPage();
        List<SgjsCriticalExpReport> sgjsCriticalExpReportList = sgjsCriticalExpReportService.getSgjsCriticalExpReportList(queryVo);
        return getDataTableAjaxResult(sgjsCriticalExpReportList);
    }

    @PreAuthorize(hasPermi = "sgjsCriticalExpReport:add")
    @PostMapping("/add")
    @CustomLogger(title = "施工技术-试验管理-工程关键部位试验报告",name = "工程关键部位试验报告",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertSgjsCriticalExpReport(@Validated(ValidationGroups.Save.class) @RequestBody SgjsCriticalExpReport sgjsCriticalExpReportParam) {
        sgjsCriticalExpReportService.insertSgjsCriticalExpReport(sgjsCriticalExpReportParam);
        return AjaxResult.success(sgjsCriticalExpReportParam);
    }

    @PreAuthorize(hasPermi = "sgjsCriticalExpReport:save")
    @PostMapping("/save")
    @CustomLogger(title = "施工技术-试验管理-工程关键部位试验报告",name = "工程关键部位试验报告",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertSgjsCriticalExpReportList(@Validated(ValidationGroups.Save.class) @RequestBody CriticalExpReportVo criticalExpReportVo) {
        sgjsCriticalExpReportService.save(criticalExpReportVo);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "sgjsCriticalExpReport:update")
    @PostMapping("/update")
    @CustomLogger(title = "施工技术-试验管理-工程关键部位试验报告",name = "工程关键部位试验报告",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateSgjsCriticalExpReport(@Validated(ValidationGroups.Update.class) @RequestBody SgjsCriticalExpReport sgjsCriticalExpReportParam) {
        return toAjax(sgjsCriticalExpReportService.updateSgjsCriticalExpReport(sgjsCriticalExpReportParam));
    }

    @PreAuthorize(hasPermi = "sgjsCriticalExpReport:update")
    @PostMapping("/batchUpdate")
    @CustomLogger(title = "施工技术-试验管理-工程关键部位试验报告",name = "工程关键部位试验报告",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateSgjsCriticalExpReportList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsCriticalExpReport> sgjsCriticalExpReportListParam) {
        return toAjax(sgjsCriticalExpReportService.updateSgjsCriticalExpReportList(sgjsCriticalExpReportListParam));
    }

    @PreAuthorize(hasPermi = "sgjsCriticalExpReport:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsCriticalExpReport(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsCriticalExpReport sgjsCriticalExpReportParam) {
        return toAjax(sgjsCriticalExpReportService.deleteSgjsCriticalExpReport(sgjsCriticalExpReportParam));
    }

    @PreAuthorize(hasPermi = "sgjsCriticalExpReport:remove")
    @PostMapping("/delete/{ids}")
    public AjaxResult deleteSgjsCriticalExpReportByPks(@PathVariable Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        return toAjax(sgjsCriticalExpReportService.deleteSgjsCriticalExpReportByPks(idList));
    }

    @PreAuthorize(hasPermi = "sgjsCriticalExpReport:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response,@RequestBody CriticalExpReportQueryVo queryVo) throws IOException {
        List<Long> ids = queryVo.getIds();
        List<SgjsCriticalExpReport> sgjsCriticalExpReportList;
        if(CollectionUtils.isEmpty(ids)){
            sgjsCriticalExpReportList = sgjsCriticalExpReportService.getSgjsCriticalExpReportList(queryVo);
        }else {
            sgjsCriticalExpReportList = sgjsCriticalExpReportService.getListByIds(ids);
        }
        FtExcelUtil<SgjsCriticalExpReport> util = new FtExcelUtil<>(SgjsCriticalExpReport.class);
        util.exportExcel(response, sgjsCriticalExpReportList, DateUtils.getDate());
    }
}
