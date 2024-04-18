package com.hhwy.pm.xmsl.xmslEngineeringReport.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.xmslEngineeringReport.domain.XmslEngineeringReport;
import com.hhwy.pm.xmsl.xmslEngineeringReport.service.IXmslEngineeringReportService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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


//    @PreAuthorize(hasPermi = "xmslEngineeringReport:list")
    @CustomLogger(title = "项目设立", name = "工程量报表" ,businessType = CustomBusinessType.SELECT)
    @PostMapping("/list")
    public AjaxResult getXmslEngineeringReportList(@RequestBody @Validated(ValidationGroups.Select.class) XmslEngineeringReport xmslEngineeringReportParam) {
        List<XmslEngineeringReport> xmslEngineeringReportList = xmslEngineeringReportService.getXmslEngineeringReportList(xmslEngineeringReportParam);
        return AjaxResult.success(xmslEngineeringReportList);
    }


//    @PreAuthorize(hasPermi = "xmslEngineeringReport:list")
    @PostMapping("/getTreeListByPid")
    public AjaxResult getTreeListByPid(@RequestBody @Validated(ValidationGroups.Select.class) XmslEngineeringReport xmslEngineeringReportParam) {
        List<XmslEngineeringReport> xmslEngineeringReportList = xmslEngineeringReportService.getTreeListByPid(xmslEngineeringReportParam);
        return AjaxResult.success(xmslEngineeringReportList);
    }

    @PreAuthorize(hasPermi = "xmslEngineeringReport:export")
    @CustomLogger(title = "项目设立", name = "工程量报表" ,businessType = CustomBusinessType.EXPORT)
    @PostMapping("/exportData")
    public void exportData(HttpServletRequest request,HttpServletResponse response, @RequestBody XmslEngineeringReport xmslEngineeringReportParam) throws IOException {
        if(xmslEngineeringReportParam.getReportType() == 2){ //清单
            List xmslEngineeringReportList = xmslEngineeringReportService.getList(xmslEngineeringReportParam);
            FtExcelUtil<XmslEngineeringReport> util = new FtExcelUtil<>(XmslEngineeringReport.class);
            xmslEngineeringReportList = TreeUtil.exportListFormat(xmslEngineeringReportList);
            util.exportExcel(response, xmslEngineeringReportList, "数据","工程量报表",Arrays.asList("清单编码","清单名称","清单单位","合同总数量"
                    ,"WBS编码","WBS名称","节点类型","本部位复核数量"));
            return ;
        }
        List xmslEngineeringReportList = xmslEngineeringReportService.getList(xmslEngineeringReportParam);
        FtExcelUtil<XmslEngineeringReport> util = new FtExcelUtil<>(XmslEngineeringReport.class);
        xmslEngineeringReportList = TreeUtil.exportListFormat(xmslEngineeringReportList);
        util.exportExcel(response, xmslEngineeringReportList, DateUtils.getDate(),"工程量报表");
    }

    /**
     * 同步
     * @return
     */
    @PostMapping("/sync")
    public AjaxResult sync() {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        xmslEngineeringReportService.sync(SecurityUtils.getTenantKey());
        return AjaxResult.success();
    }

    /**
     * 获取wbs下的工程量清单
     * @param xmslEngineeringReportParam
     * @return
     */
    @PostMapping("/relateList")
    public AjaxResult relateList(@RequestBody XmslEngineeringReport xmslEngineeringReportParam) {
        Map<String,List<XmslContractList>> map = xmslEngineeringReportService.relateListByWbsCode(xmslEngineeringReportParam.getWbsCode());
        return AjaxResult.success(map);
    }
}
