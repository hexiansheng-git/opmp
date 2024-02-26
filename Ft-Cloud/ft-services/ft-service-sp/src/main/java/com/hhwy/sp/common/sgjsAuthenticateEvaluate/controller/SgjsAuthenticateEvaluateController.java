package com.hhwy.sp.common.sgjsAuthenticateEvaluate.controller;

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
import com.hhwy.sp.common.sgjsAuthenticateEvaluate.service.ISgjsAuthenticateEvaluateService;
import com.hhwy.sp.common.sgjsAuthenticateEvaluate.domain.SgjsAuthenticateEvaluate;

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
public class SgjsAuthenticateEvaluateController extends BaseController {

    @Autowired
    private ISgjsAuthenticateEvaluateService shjsAuthenticateEvaluateService;


    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:list")
    @GetMapping
    public AjaxResult getShjsAuthenticateEvaluate(@Validated(ValidationGroups.Get.class) SgjsAuthenticateEvaluate shjsAuthenticateEvaluateParam) {
        SgjsAuthenticateEvaluate shjsAuthenticateEvaluate = shjsAuthenticateEvaluateService.getShjsAuthenticateEvaluate(shjsAuthenticateEvaluateParam);
        return AjaxResult.success(shjsAuthenticateEvaluate);
    }

    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:list")
    @GetMapping("/list")
    public AjaxResult getShjsAuthenticateEvaluateList(@Validated(ValidationGroups.Select.class) SgjsAuthenticateEvaluate shjsAuthenticateEvaluateParam) {
        startPage();
        List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluateList = shjsAuthenticateEvaluateService.getShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateParam);
        return getDataTableAjaxResult(shjsAuthenticateEvaluateList);
    }

    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:add")
    @PostMapping("/add")
    public AjaxResult insertShjsAuthenticateEvaluate(@Validated(ValidationGroups.Save.class) @RequestBody SgjsAuthenticateEvaluate shjsAuthenticateEvaluateParam) {
        shjsAuthenticateEvaluateService.insertShjsAuthenticateEvaluate(shjsAuthenticateEvaluateParam);
        return AjaxResult.success(shjsAuthenticateEvaluateParam);
    }

    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertShjsAuthenticateEvaluateList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluateListParam) {
        shjsAuthenticateEvaluateService.insertShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateListParam);
        return AjaxResult.success(shjsAuthenticateEvaluateListParam);
    }

    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:update")
    @PostMapping("/update")
    public AjaxResult updateShjsAuthenticateEvaluate(@Validated(ValidationGroups.Update.class) @RequestBody SgjsAuthenticateEvaluate shjsAuthenticateEvaluateParam) {
        return toAjax(shjsAuthenticateEvaluateService.updateShjsAuthenticateEvaluate(shjsAuthenticateEvaluateParam));
    }

    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateShjsAuthenticateEvaluateList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluateListParam) {
        return toAjax(shjsAuthenticateEvaluateService.updateShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateListParam));
    }

    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:remove")
    @PostMapping("/delete")
    public AjaxResult deleteShjsAuthenticateEvaluate(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsAuthenticateEvaluate shjsAuthenticateEvaluateParam) {
        return toAjax(shjsAuthenticateEvaluateService.deleteShjsAuthenticateEvaluate(shjsAuthenticateEvaluateParam));
    }

    @PreAuthorize(hasPermi = "shjsAuthenticateEvaluate:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteShjsAuthenticateEvaluateByPks(@PathVariable Long[] ids) {
        List<Long> shjsAuthenticateEvaluatePkList = Arrays.asList(ids);
        return toAjax(shjsAuthenticateEvaluateService.deleteShjsAuthenticateEvaluateByPks(shjsAuthenticateEvaluatePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsAuthenticateEvaluate shjsAuthenticateEvaluateParam) throws IOException {
        List<SgjsAuthenticateEvaluate> shjsAuthenticateEvaluateList = shjsAuthenticateEvaluateService.getShjsAuthenticateEvaluateList(shjsAuthenticateEvaluateParam);
        ExcelUtils<SgjsAuthenticateEvaluate> util = new ExcelUtils<>(SgjsAuthenticateEvaluate.class);
        util.exportExcel(response, shjsAuthenticateEvaluateList, DateUtils.getDate());
    }
}
