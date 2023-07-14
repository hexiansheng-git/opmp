package com.hhwy.pm.xmsl.wbs.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * wbs
 * @author wk
 * @date 2023-07-13 18:13:13
 * @remark
 */
@Validated
@RestController
@RequestMapping("/xmslWbs")
public class XmslWbsController extends BaseController {

    @Autowired
    private IXmslWbsService xmslWbsService;


    @PreAuthorize(hasPermi = "xmslWbs:list")
    @GetMapping
    public AjaxResult getXmslWbs(@Validated(ValidationGroups.Get.class) @RequestBody XmslWbs xmslWbsParam) {
        XmslWbs xmslWbs = xmslWbsService.getXmslWbs(xmslWbsParam);
        return AjaxResult.success(xmslWbs);
    }

    @PreAuthorize(hasPermi = "xmslWbs:list")
    @GetMapping("/list")
    public AjaxResult getXmslWbsList(@Validated(ValidationGroups.Select.class) @RequestBody XmslWbs xmslWbsParam) {
        startPage();
        List<XmslWbs> xmslWbsList = xmslWbsService.getXmslWbsList(xmslWbsParam);
        return getDataTableAjaxResult(xmslWbsList);
    }

    @PreAuthorize(hasPermi = "xmslWbs:add")
    @PostMapping("/add")
    public AjaxResult insertXmslWbs(@Validated(ValidationGroups.Save.class) @RequestBody XmslWbs xmslWbsParam) {
        xmslWbsService.insertXmslWbs(xmslWbsParam);
        return AjaxResult.success(xmslWbsParam);
    }

    @PreAuthorize(hasPermi = "xmslWbs:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertXmslWbsList(@Validated(ValidationGroups.Save.class) @RequestBody List<XmslWbs> xmslWbsListParam) {
        xmslWbsService.insertXmslWbsList(xmslWbsListParam);
        return AjaxResult.success(xmslWbsListParam);
    }

    @PreAuthorize(hasPermi = "xmslWbs:update")
    @PostMapping("/update")
    public AjaxResult updateXmslWbs(@Validated(ValidationGroups.Update.class) @RequestBody XmslWbs xmslWbsParam) {
        return toAjax(xmslWbsService.updateXmslWbs(xmslWbsParam));
    }

    @PreAuthorize(hasPermi = "xmslWbs:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateXmslWbsList(@Validated(ValidationGroups.Update.class) @RequestBody List<XmslWbs> xmslWbsListParam) {
        return toAjax(xmslWbsService.updateXmslWbsList(xmslWbsListParam));
    }

    @PreAuthorize(hasPermi = "xmslWbs:remove")
    @PostMapping("/delete")
    public AjaxResult deleteXmslWbs(@Validated(ValidationGroups.Delete.class) @RequestBody XmslWbs xmslWbsParam) {
        return toAjax(xmslWbsService.deleteXmslWbs(xmslWbsParam));
    }

    @PreAuthorize(hasPermi = "xmslWbs:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteXmslWbsByPks(@PathVariable Long[] ids) {
        List<Long> xmslWbsPkList = Arrays.asList(ids);
        return toAjax(xmslWbsService.deleteXmslWbsByPks(xmslWbsPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslWbs xmslWbsParam) throws IOException {
        List<XmslWbs> xmslWbsList = xmslWbsService.getXmslWbsList(xmslWbsParam);
        ExcelUtils<XmslWbs> util = new ExcelUtils<>(XmslWbs.class);
        util.exportExcel(response, xmslWbsList, DateUtils.getDate());
    }
}
