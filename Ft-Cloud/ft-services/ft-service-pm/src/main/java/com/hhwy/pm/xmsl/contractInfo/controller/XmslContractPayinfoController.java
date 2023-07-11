package com.hhwy.pm.xmsl.contractInfo.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractPayinfoService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:46
 * @remark
 */
@Validated
@RestController
@RequestMapping("/xmslContractPayinfo")
public class XmslContractPayinfoController extends BaseController {

    @Autowired
    private IXmslContractPayinfoService xmslContractPayinfoService;


    @PreAuthorize(hasPermi = "xmslContractPayinfo:list")
    @GetMapping
    public AjaxResult getXmslContractPayinfo(@Validated(ValidationGroups.Get.class) @RequestBody XmslContractPayinfo xmslContractPayinfoParam) {
        XmslContractPayinfo xmslContractPayinfo = xmslContractPayinfoService.getXmslContractPayinfo(xmslContractPayinfoParam);
        return AjaxResult.success(xmslContractPayinfo);
    }

    @PreAuthorize(hasPermi = "xmslContractPayinfo:list")
    @GetMapping("/list")
    public AjaxResult getXmslContractPayinfoList(@Validated(ValidationGroups.Select.class) @RequestBody XmslContractPayinfo xmslContractPayinfoParam) {
        startPage();
        List<XmslContractPayinfo> xmslContractPayinfoList = xmslContractPayinfoService.getXmslContractPayinfoList(xmslContractPayinfoParam);
        return getDataTableAjaxResult(xmslContractPayinfoList);
    }

    @PreAuthorize(hasPermi = "xmslContractPayinfo:add")
    @PostMapping("/add")
    public AjaxResult insertXmslContractPayinfo(@Validated(ValidationGroups.Save.class) @RequestBody XmslContractPayinfo xmslContractPayinfoParam) {
        xmslContractPayinfoService.insertXmslContractPayinfo(xmslContractPayinfoParam);
        return AjaxResult.success(xmslContractPayinfoParam);
    }

    @PreAuthorize(hasPermi = "xmslContractPayinfo:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertXmslContractPayinfoList(@Validated(ValidationGroups.Save.class) @RequestBody List<XmslContractPayinfo> xmslContractPayinfoListParam) {
        xmslContractPayinfoService.insertXmslContractPayinfoList(xmslContractPayinfoListParam);
        return AjaxResult.success(xmslContractPayinfoListParam);
    }

    @PreAuthorize(hasPermi = "xmslContractPayinfo:update")
    @PostMapping("/update")
    public AjaxResult updateXmslContractPayinfo(@Validated(ValidationGroups.Update.class) @RequestBody XmslContractPayinfo xmslContractPayinfoParam) {
        return toAjax(xmslContractPayinfoService.updateXmslContractPayinfo(xmslContractPayinfoParam));
    }

    @PreAuthorize(hasPermi = "xmslContractPayinfo:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateXmslContractPayinfoList(@Validated(ValidationGroups.Update.class) @RequestBody List<XmslContractPayinfo> xmslContractPayinfoListParam) {
        return toAjax(xmslContractPayinfoService.updateXmslContractPayinfoList(xmslContractPayinfoListParam));
    }

    @PreAuthorize(hasPermi = "xmslContractPayinfo:remove")
    @PostMapping("/delete")
    public AjaxResult deleteXmslContractPayinfo(@Validated(ValidationGroups.Delete.class) @RequestBody XmslContractPayinfo xmslContractPayinfoParam) {
        return toAjax(xmslContractPayinfoService.deleteXmslContractPayinfo(xmslContractPayinfoParam));
    }

    @PreAuthorize(hasPermi = "xmslContractPayinfo:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteXmslContractPayinfoByPks(@PathVariable Long[] ids) {
        List<Long> xmslContractPayinfoPkList = Arrays.asList(ids);
        return toAjax(xmslContractPayinfoService.deleteXmslContractPayinfoByPks(xmslContractPayinfoPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslContractPayinfo xmslContractPayinfoParam) throws IOException {
        List<XmslContractPayinfo> xmslContractPayinfoList = xmslContractPayinfoService.getXmslContractPayinfoList(xmslContractPayinfoParam);
        ExcelUtils<XmslContractPayinfo> util = new ExcelUtils<>(XmslContractPayinfo.class);
        util.exportExcel(response, xmslContractPayinfoList, DateUtils.getDate());
    }
}
