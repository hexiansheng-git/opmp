package com.hhwy.pm.qqch.module.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
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
 * @date 2023-07-11 15:23:04
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchModuleConfirmCase")
public class QqchModuleConfirmCaseController extends BaseController {

    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    @PreAuthorize(hasPermi = "qqchModuleConfirmCase:list")
    @GetMapping
    public AjaxResult getQqchModuleConfirmCase(@Validated(ValidationGroups.Get.class) QqchModuleConfirmCase qqchModuleConfirmCaseParam) {
        QqchModuleConfirmCase qqchModuleConfirmCase = qqchModuleConfirmCaseService.getQqchModuleConfirmCase(qqchModuleConfirmCaseParam);
        return AjaxResult.success(qqchModuleConfirmCase);
    }

    @PreAuthorize(hasPermi = "qqchModuleConfirmCase:list")
    @GetMapping("/list")
    public AjaxResult getQqchModuleConfirmCaseList(@Validated(ValidationGroups.Select.class) QqchModuleConfirmCase qqchModuleConfirmCaseParam) {
        startPage();
        List<QqchModuleConfirmCase> qqchModuleConfirmCaseList = qqchModuleConfirmCaseService.getQqchModuleConfirmCaseList(qqchModuleConfirmCaseParam);
        return getDataTableAjaxResult(qqchModuleConfirmCaseList);
    }

    @PreAuthorize(hasPermi = "qqchModuleConfirmCase:add")
    @PostMapping("/add")
    public AjaxResult insertQqchModuleConfirmCase(@Validated(ValidationGroups.Save.class) @RequestBody QqchModuleConfirmCase qqchModuleConfirmCaseParam) {
        qqchModuleConfirmCaseService.insertQqchModuleConfirmCase(qqchModuleConfirmCaseParam);
        return AjaxResult.success(qqchModuleConfirmCaseParam);
    }

    @PreAuthorize(hasPermi = "qqchModuleConfirmCase:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchModuleConfirmCaseList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchModuleConfirmCase> qqchModuleConfirmCaseListParam) {
        qqchModuleConfirmCaseService.insertQqchModuleConfirmCaseList(qqchModuleConfirmCaseListParam);
        return AjaxResult.success(qqchModuleConfirmCaseListParam);
    }

    @PreAuthorize(hasPermi = "qqchModuleConfirmCase:update")
    @PostMapping("/update")
    public AjaxResult updateQqchModuleConfirmCase(@Validated(ValidationGroups.Update.class) @RequestBody QqchModuleConfirmCase qqchModuleConfirmCaseParam) {
        return toAjax(qqchModuleConfirmCaseService.updateQqchModuleConfirmCase(qqchModuleConfirmCaseParam));
    }

    @PreAuthorize(hasPermi = "qqchModuleConfirmCase:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchModuleConfirmCaseList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchModuleConfirmCase> qqchModuleConfirmCaseListParam) {
        return toAjax(qqchModuleConfirmCaseService.updateQqchModuleConfirmCaseList(qqchModuleConfirmCaseListParam));
    }

    @PreAuthorize(hasPermi = "qqchModuleConfirmCase:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchModuleConfirmCase(@Validated(ValidationGroups.Delete.class) @RequestBody QqchModuleConfirmCase qqchModuleConfirmCaseParam) {
        return toAjax(qqchModuleConfirmCaseService.deleteQqchModuleConfirmCase(qqchModuleConfirmCaseParam));
    }

    @PreAuthorize(hasPermi = "qqchModuleConfirmCase:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchModuleConfirmCaseByPks(@PathVariable Long[] ids) {
        List<Long> qqchModuleConfirmCasePkList = Arrays.asList(ids);
        return toAjax(qqchModuleConfirmCaseService.deleteQqchModuleConfirmCaseByPks(qqchModuleConfirmCasePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchModuleConfirmCase qqchModuleConfirmCaseParam) throws IOException {
        List<QqchModuleConfirmCase> qqchModuleConfirmCaseList = qqchModuleConfirmCaseService.getQqchModuleConfirmCaseList(qqchModuleConfirmCaseParam);
        ExcelUtils<QqchModuleConfirmCase> util = new ExcelUtils<>(QqchModuleConfirmCase.class);
        util.exportExcel(response, qqchModuleConfirmCaseList, DateUtils.getDate());
    }
}
