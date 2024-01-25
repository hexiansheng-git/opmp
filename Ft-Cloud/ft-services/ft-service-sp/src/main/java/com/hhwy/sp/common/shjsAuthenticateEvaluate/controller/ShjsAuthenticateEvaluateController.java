package com.hhwy.sp.common.shjsAuthenticateEvaluate.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.service.IShjsAuthenticateEvaluateService;
import com.hhwy.sp.common.shjsAuthenticateEvaluate.domain.ShjsAuthenticateEvaluate;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * 功能描述: 科技管理 - 鉴定或评价
 * 作者: fushudong
 * 时间: 2024/1/25
 */
@Validated
@RestController
@RequestMapping("/shjsAuthenticateEvaluate")
public class ShjsAuthenticateEvaluateController extends BaseController {

    @Autowired
    private IShjsAuthenticateEvaluateService shjsAuthenticateEvaluateService;


    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:list")
    @GetMapping
    public AjaxResult getShjsAuthenticateEvaluate(@Validated(ValidationGroups.Get.class) ShjsAuthenticateEvaluate shjsAuthenticateEvaluateParam) {
        ShjsAuthenticateEvaluate shjsAuthenticateEvaluate = shjsAuthenticateEvaluateService.getShjsAuthenticateEvaluate(shjsAuthenticateEvaluateParam);
        return AjaxResult.success(shjsAuthenticateEvaluate);
    }

    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:list")
    @GetMapping("/list")
    public AjaxResult getShjsAuthenticateEvaluateList(@Validated(ValidationGroups.Select.class) ShjsAuthenticateEvaluate shjsAuthenticateEvaluateParam) {
        startPage();
        List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList = shjsAuthenticateEvaluateService.getShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateParam);
        return getDataTableAjaxResult(shjsAuthenticateEvaluateList);
    }

    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:add")
    @PostMapping("/add")
    public AjaxResult insertShjsAuthenticateEvaluate(@Validated(ValidationGroups.Save.class) @RequestBody ShjsAuthenticateEvaluate shjsAuthenticateEvaluateParam) {
        shjsAuthenticateEvaluateService.insertShjsAuthenticateEvaluate(shjsAuthenticateEvaluateParam);
        return AjaxResult.success(shjsAuthenticateEvaluateParam);
    }

    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertShjsAuthenticateEvaluateList(@Validated(ValidationGroups.Save.class) @RequestBody List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateListParam) {
        shjsAuthenticateEvaluateService.insertShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateListParam);
        return AjaxResult.success(shjsAuthenticateEvaluateListParam);
    }

    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:update")
    @PostMapping("/update")
    public AjaxResult updateShjsAuthenticateEvaluate(@Validated(ValidationGroups.Update.class) @RequestBody ShjsAuthenticateEvaluate shjsAuthenticateEvaluateParam) {
        return toAjax(shjsAuthenticateEvaluateService.updateShjsAuthenticateEvaluate(shjsAuthenticateEvaluateParam));
    }

    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateShjsAuthenticateEvaluateList(@Validated(ValidationGroups.Update.class) @RequestBody List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateListParam) {
        return toAjax(shjsAuthenticateEvaluateService.updateShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateListParam));
    }

    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:remove")
    @PostMapping("/delete")
    public AjaxResult deleteShjsAuthenticateEvaluate(@Validated(ValidationGroups.Delete.class) @RequestBody ShjsAuthenticateEvaluate shjsAuthenticateEvaluateParam) {
        return toAjax(shjsAuthenticateEvaluateService.deleteShjsAuthenticateEvaluate(shjsAuthenticateEvaluateParam));
    }

    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteShjsAuthenticateEvaluateByPks(@PathVariable Long[] ids) {
        List<Long> shjsAuthenticateEvaluatePkList = Arrays.asList(ids);
        return toAjax(shjsAuthenticateEvaluateService.deleteShjsAuthenticateEvaluateByPks(shjsAuthenticateEvaluatePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, ShjsAuthenticateEvaluate shjsAuthenticateEvaluateParam) throws IOException {
        List<ShjsAuthenticateEvaluate> shjsAuthenticateEvaluateList = shjsAuthenticateEvaluateService.getShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateParam);
        ExcelUtils<ShjsAuthenticateEvaluate> util = new ExcelUtils<>(ShjsAuthenticateEvaluate.class);
        util.exportExcel(response, shjsAuthenticateEvaluateList, DateUtils.getDate());
    }
}
