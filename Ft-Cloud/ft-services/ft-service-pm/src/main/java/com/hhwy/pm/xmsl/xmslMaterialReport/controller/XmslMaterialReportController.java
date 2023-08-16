package com.hhwy.pm.xmsl.xmslMaterialReport.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.xmslMaterialReport.domain.XmslMaterialReport;
import com.hhwy.pm.xmsl.xmslMaterialReport.service.IXmslMaterialReportService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 主材报表
 *
 * @author wk
 * @date 2023-08-15 17:39:46
 * @remark
 */
@Validated
@RestController
@RequestMapping("/xmslMaterialReport")
public class XmslMaterialReportController extends BaseController {

    @Autowired
    private IXmslMaterialReportService xmslMaterialReportService;

    @PreAuthorize(hasPermi = "xmslMaterialReport:list")
    @GetMapping("/list")
    public AjaxResult getXmslMaterialReportList(@Validated(ValidationGroups.Select.class) XmslMaterialReport xmslMaterialReportParam) {
        startPage();
        List<XmslMaterialReport> xmslMaterialReportList = xmslMaterialReportService.getXmslMaterialReportList(xmslMaterialReportParam);
        return getDataTableAjaxResult(xmslMaterialReportList);
    }


    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslMaterialReport xmslMaterialReportParam) throws IOException {
        List<XmslMaterialReport> xmslMaterialReportList = xmslMaterialReportService.getXmslMaterialReportList(xmslMaterialReportParam);
        ExcelUtils<XmslMaterialReport> util = new ExcelUtils<>(XmslMaterialReport.class);
        util.exportExcel(response, xmslMaterialReportList, DateUtils.getDate());
    }

    /**
     * 同步
     * @return
     */
    @PostMapping("/sync")
    public AjaxResult sync(@RequestBody Map map) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        xmslMaterialReportService.sync(ObjectUtils.nvlLong(map.get("id")));
        return AjaxResult.success();
    }
}
