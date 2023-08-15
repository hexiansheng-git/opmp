package com.hhwy.pm.xmsl.xmslEngineeringReport.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.xmslEngineeringReport.domain.XmslEngineeringReport;
import com.hhwy.pm.xmsl.xmslEngineeringReport.service.IXmslEngineeringReportService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 工程量报表
 * @author wk
 * @date 2023-08-14 13:48:27
 * @remark
 */
@Validated
@RestController
@RequestMapping("/xmslEngineeringReport")
public class XmslEngineeringReportController extends BaseController {
    @Autowired
    private IXmslEngineeringReportService xmslEngineeringReportService;


    @PreAuthorize(hasPermi = "xmslEngineeringReport:list")
    @GetMapping("/list")
    public AjaxResult getXmslEngineeringReportList(@Validated(ValidationGroups.Select.class) XmslEngineeringReport xmslEngineeringReportParam) {
        startPage();
        List<XmslEngineeringReport> xmslEngineeringReportList = xmslEngineeringReportService.getXmslEngineeringReportList(xmslEngineeringReportParam);
        return getDataTableAjaxResult(xmslEngineeringReportList);
    }

    @PreAuthorize(hasPermi = "xmslEngineeringReport:add")
    @PostMapping("/add")
    public AjaxResult insertXmslEngineeringReport(@Validated(ValidationGroups.Save.class) @RequestBody XmslEngineeringReport xmslEngineeringReportParam) {
        xmslEngineeringReportService.insertXmslEngineeringReport(xmslEngineeringReportParam);
        return AjaxResult.success(xmslEngineeringReportParam);
    }

    @PreAuthorize(hasPermi = "xmslEngineeringReport:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertXmslEngineeringReportList(@Validated(ValidationGroups.Save.class) @RequestBody List<XmslEngineeringReport> xmslEngineeringReportListParam) {
        xmslEngineeringReportService.insertXmslEngineeringReportList(xmslEngineeringReportListParam);
        return AjaxResult.success(xmslEngineeringReportListParam);
    }

    @PreAuthorize(hasPermi = "xmslEngineeringReport:update")
    @PostMapping("/update")
    public AjaxResult updateXmslEngineeringReport(@Validated(ValidationGroups.Update.class) @RequestBody XmslEngineeringReport xmslEngineeringReportParam) {
        return toAjax(xmslEngineeringReportService.updateXmslEngineeringReport(xmslEngineeringReportParam));
    }

    @PreAuthorize(hasPermi = "xmslEngineeringReport:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateXmslEngineeringReportList(@Validated(ValidationGroups.Update.class) @RequestBody List<XmslEngineeringReport> xmslEngineeringReportListParam) {
        return toAjax(xmslEngineeringReportService.updateXmslEngineeringReportList(xmslEngineeringReportListParam));
    }

    @PreAuthorize(hasPermi = "xmslEngineeringReport:remove")
    @PostMapping("/delete")
    public AjaxResult deleteXmslEngineeringReport(@Validated(ValidationGroups.Delete.class) @RequestBody XmslEngineeringReport xmslEngineeringReportParam) {
        return toAjax(xmslEngineeringReportService.deleteXmslEngineeringReport(xmslEngineeringReportParam));
    }

    @PreAuthorize(hasPermi = "xmslEngineeringReport:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteXmslEngineeringReportByPks(@PathVariable Long[] ids) {
        List<Long> xmslEngineeringReportPkList = Arrays.asList(ids);
        return toAjax(xmslEngineeringReportService.deleteXmslEngineeringReportByPks(xmslEngineeringReportPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslEngineeringReport xmslEngineeringReportParam) throws IOException {
        List<XmslEngineeringReport> xmslEngineeringReportList = xmslEngineeringReportService.getXmslEngineeringReportList(xmslEngineeringReportParam);
        ExcelUtils<XmslEngineeringReport> util = new ExcelUtils<>(XmslEngineeringReport.class);
        util.exportExcel(response, xmslEngineeringReportList, DateUtils.getDate());
    }

    /**
     * 同步
     * @return
     */
    @PostMapping("/sync")
    public AjaxResult sync() {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        xmslEngineeringReportService.sync();
        return AjaxResult.success();
    }
}
