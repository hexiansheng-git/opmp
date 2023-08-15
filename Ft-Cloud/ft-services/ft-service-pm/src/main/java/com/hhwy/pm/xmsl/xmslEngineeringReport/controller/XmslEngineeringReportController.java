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
    @PostMapping("/list")
    public AjaxResult getXmslEngineeringReportList(@RequestBody @Validated(ValidationGroups.Select.class) XmslEngineeringReport xmslEngineeringReportParam) {
        List<XmslEngineeringReport> xmslEngineeringReportList = xmslEngineeringReportService.getXmslEngineeringReportList(xmslEngineeringReportParam);
        return AjaxResult.success(xmslEngineeringReportList);
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
