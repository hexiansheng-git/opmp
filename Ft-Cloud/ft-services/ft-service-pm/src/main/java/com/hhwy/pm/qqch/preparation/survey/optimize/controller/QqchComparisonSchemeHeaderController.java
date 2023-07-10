package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeHeader;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeHeaderService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author han
 * @date 2023-07-07 18:35:43
 * @remark 比选方案表头
 */
@Validated
@RestController
@RequestMapping("/qqchComparisonSchemeHeader")
public class QqchComparisonSchemeHeaderController extends BaseController {

    @Autowired
    private IQqchComparisonSchemeHeaderService qqchComparisonSchemeHeaderService;


    @PreAuthorize(hasPermi = "qqchComparisonSchemeHeader:list")
    @GetMapping
    public AjaxResult getQqchComparisonSchemeHeader(@Validated(ValidationGroups.Get.class) @RequestBody QqchComparisonSchemeHeader qqchComparisonSchemeHeaderParam) {
        QqchComparisonSchemeHeader qqchComparisonSchemeHeader = qqchComparisonSchemeHeaderService.getQqchComparisonSchemeHeader(qqchComparisonSchemeHeaderParam);
        return AjaxResult.success(qqchComparisonSchemeHeader);
    }

    @PreAuthorize(hasPermi = "qqchComparisonSchemeHeader:list")
    @GetMapping("/list")
    public AjaxResult getQqchComparisonSchemeHeaderList(@Validated(ValidationGroups.Select.class) @RequestBody QqchComparisonSchemeHeader qqchComparisonSchemeHeaderParam) {
        startPage();
        List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList = qqchComparisonSchemeHeaderService.getQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeaderParam);
        return getDataTableAjaxResult(qqchComparisonSchemeHeaderList);
    }

    @PreAuthorize(hasPermi = "qqchComparisonSchemeHeader:add")
    @PostMapping("/add")
    public AjaxResult insertQqchComparisonSchemeHeader(@Validated(ValidationGroups.Save.class) @RequestBody QqchComparisonSchemeHeader qqchComparisonSchemeHeaderParam) {
        qqchComparisonSchemeHeaderService.insertQqchComparisonSchemeHeader(qqchComparisonSchemeHeaderParam);
        return AjaxResult.success(qqchComparisonSchemeHeaderParam);
    }

    @PreAuthorize(hasPermi = "qqchComparisonSchemeHeader:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchComparisonSchemeHeaderList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderListParam) {
        qqchComparisonSchemeHeaderService.insertQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeaderListParam);
        return AjaxResult.success(qqchComparisonSchemeHeaderListParam);
    }

    @PreAuthorize(hasPermi = "qqchComparisonSchemeHeader:update")
    @PostMapping("/update")
    public AjaxResult updateQqchComparisonSchemeHeader(@Validated(ValidationGroups.Update.class) @RequestBody QqchComparisonSchemeHeader qqchComparisonSchemeHeaderParam) {
        return toAjax(qqchComparisonSchemeHeaderService.updateQqchComparisonSchemeHeader(qqchComparisonSchemeHeaderParam));
    }

    @PreAuthorize(hasPermi = "qqchComparisonSchemeHeader:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchComparisonSchemeHeaderList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderListParam) {
        return toAjax(qqchComparisonSchemeHeaderService.updateQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeaderListParam));
    }

    @PreAuthorize(hasPermi = "qqchComparisonSchemeHeader:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchComparisonSchemeHeader(@Validated(ValidationGroups.Delete.class) @RequestBody QqchComparisonSchemeHeader qqchComparisonSchemeHeaderParam) {
        return toAjax(qqchComparisonSchemeHeaderService.deleteQqchComparisonSchemeHeader(qqchComparisonSchemeHeaderParam));
    }

    @PreAuthorize(hasPermi = "qqchComparisonSchemeHeader:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchComparisonSchemeHeaderByPks(@PathVariable Long[] ids) {
        List<Long> qqchComparisonSchemeHeaderPkList = Arrays.asList(ids);
        return toAjax(qqchComparisonSchemeHeaderService.deleteQqchComparisonSchemeHeaderByPks(qqchComparisonSchemeHeaderPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchComparisonSchemeHeader qqchComparisonSchemeHeaderParam) throws IOException {
        List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList = qqchComparisonSchemeHeaderService.getQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeaderParam);
        ExcelUtils<QqchComparisonSchemeHeader> util = new ExcelUtils<>(QqchComparisonSchemeHeader.class);
        util.exportExcel(response, qqchComparisonSchemeHeaderList, DateUtils.getDate());
    }
}
