package com.hhwy.sp.common.sgjsExpertLibrary.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.common.sgjsExpertLibrary.service.ISgjsExpertLibraryService;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/***
 * 功能描述: 科技管理 - 专家库
 * 作者: fushudong
 * 时间: 2024/1/25
 */
@Validated
@RestController
@RequestMapping("/sgjsExpertLibrary")
public class SgjsExpertLibraryController extends BaseController {

    @Autowired
    private ISgjsExpertLibraryService sgjsExpertLibraryService;


    @PreAuthorize(hasPermi = "sgjsExpertLibrary:list")
    @GetMapping
    public AjaxResult getSgjsExpertLibrary(@Validated(ValidationGroups.Get.class) SgjsExpertLibrary sgjsExpertLibraryParam) {
        SgjsExpertLibrary sgjsExpertLibrary = sgjsExpertLibraryService.getSgjsExpertLibrary(sgjsExpertLibraryParam);
        return AjaxResult.success(sgjsExpertLibrary);
    }

    @PreAuthorize(hasPermi = "sgjsExpertLibrary:list")
    @GetMapping("/list")
    public AjaxResult getSgjsExpertLibraryList(@Validated(ValidationGroups.Select.class) SgjsExpertLibrary sgjsExpertLibraryParam) {
        startPage();
        List<SgjsExpertLibrary> sgjsExpertLibraryList = sgjsExpertLibraryService.getSgjsExpertLibraryList(sgjsExpertLibraryParam);
        return getDataTableAjaxResult(sgjsExpertLibraryList);
    }

    @PreAuthorize(hasPermi = "sgjsExpertLibrary:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsExpertLibrary(@Validated(ValidationGroups.Save.class) @RequestBody SgjsExpertLibrary sgjsExpertLibraryParam) {
        sgjsExpertLibraryService.insertSgjsExpertLibrary(sgjsExpertLibraryParam);
        return AjaxResult.success(sgjsExpertLibraryParam);
    }

    @PreAuthorize(hasPermi = "sgjsExpertLibrary:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsExpertLibraryList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsExpertLibrary> sgjsExpertLibraryListParam) {
        sgjsExpertLibraryService.insertSgjsExpertLibraryList(sgjsExpertLibraryListParam);
        return AjaxResult.success(sgjsExpertLibraryListParam);
    }

    @PreAuthorize(hasPermi = "sgjsExpertLibrary:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsExpertLibrary(@Validated(ValidationGroups.Update.class) @RequestBody SgjsExpertLibrary sgjsExpertLibraryParam) {
        return toAjax(sgjsExpertLibraryService.updateSgjsExpertLibrary(sgjsExpertLibraryParam));
    }

    @PreAuthorize(hasPermi = "sgjsExpertLibrary:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsExpertLibraryList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsExpertLibrary> sgjsExpertLibraryListParam) {
        return toAjax(sgjsExpertLibraryService.updateSgjsExpertLibraryList(sgjsExpertLibraryListParam));
    }

    @PreAuthorize(hasPermi = "sgjsExpertLibrary:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsExpertLibrary(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsExpertLibrary sgjsExpertLibraryParam) {
        return toAjax(sgjsExpertLibraryService.deleteSgjsExpertLibrary(sgjsExpertLibraryParam));
    }

    @PreAuthorize(hasPermi = "sgjsExpertLibrary:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsExpertLibraryByPks(@PathVariable Long[] ids) {
        List<Long> sgjsExpertLibraryPkList = Arrays.asList(ids);
        return toAjax(sgjsExpertLibraryService.deleteSgjsExpertLibraryByPks(sgjsExpertLibraryPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsExpertLibrary sgjsExpertLibraryParam) throws IOException {
        List<SgjsExpertLibrary> sgjsExpertLibraryList = sgjsExpertLibraryService.getSgjsExpertLibraryList(sgjsExpertLibraryParam);
        ExcelUtils<SgjsExpertLibrary> util = new ExcelUtils<>(SgjsExpertLibrary.class);
        util.exportExcel(response, sgjsExpertLibraryList, DateUtils.getDate());
    }
}
