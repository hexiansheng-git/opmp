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
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.excel.FtExcelUtil;
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

//    @PreAuthorize(hasPermi = "xmslMaterialReport:list")
    @CustomLogger(title = "项目设立", name = "主材报表" ,businessType = CustomBusinessType.SELECT)
    @GetMapping("/list")
    public AjaxResult getXmslMaterialReportList(@Validated(ValidationGroups.Select.class) XmslMaterialReport xmslMaterialReportParam) {
        startPage();
        List<XmslMaterialReport> xmslMaterialReportList = xmslMaterialReportService.getXmslMaterialReportList(xmslMaterialReportParam);
        return getDataTableAjaxResult(xmslMaterialReportList);
    }

    /**
     * 为1.6物资总需用提供，将type换成物资信息的materialType
     * @param xmslMaterialReportParam
     * @return
     */
    @GetMapping("/listForTotalDemand")
    public AjaxResult listForTotalDemand(@Validated(ValidationGroups.Select.class) XmslMaterialReport xmslMaterialReportParam) {
        startPage();
        List<XmslMaterialReport> xmslMaterialReportList = xmslMaterialReportService.listForTotalDemand(xmslMaterialReportParam);
        return getDataTableAjaxResult(xmslMaterialReportList);
    }


    @CustomLogger(title = "项目设立", name = "主材报表" ,businessType = CustomBusinessType.EXPORT)
    @GetMapping("/exportData")
    public void export(HttpServletResponse response) throws IOException {
        List<XmslMaterialReport> xmslMaterialReportList = xmslMaterialReportService.getXmslMaterialReportList(new XmslMaterialReport());
        FtExcelUtil<XmslMaterialReport> util = new FtExcelUtil<>(XmslMaterialReport.class);
        util.exportExcel(response, xmslMaterialReportList, DateUtils.getDate(),"主材报表");
    }

    /**
     * 同步
     * @return
     */
    @PostMapping("/sync")
    public AjaxResult sync(@RequestBody Map map) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        xmslMaterialReportService.sync(ObjectUtils.nvlLong(map.get("id")),SecurityUtils.getTenantKey());
        return AjaxResult.success();
    }
}
