package com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.controller;

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
import com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.service.ISgjsSpecialMeasureService;
import com.hhwy.sp.sgjsMeasure.sgjsSpecialMeasure.domain.SgjsSpecialMeasure;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * 施工技术--测量管理--特殊工程监控量测
 *
 * @author lcf
 * @date 2024-03-12 14:54:37
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsSpecialMeasure")
public class SgjsSpecialMeasureController extends BaseController {

    @Autowired
    private ISgjsSpecialMeasureService sgjsSpecialMeasureService;


    @PreAuthorize(hasPermi = "sgjsSpecialMeasure:list")
    @GetMapping
    public AjaxResult getSgjsSpecialMeasure(@Validated(ValidationGroups.Get.class) SgjsSpecialMeasure sgjsSpecialMeasureParam) {
        SgjsSpecialMeasure sgjsSpecialMeasure = sgjsSpecialMeasureService.getSgjsSpecialMeasure(sgjsSpecialMeasureParam);
        return AjaxResult.success(sgjsSpecialMeasure);
    }

    @PreAuthorize(hasPermi = "sgjsSpecialMeasure:list")
    @GetMapping("/list")
    public AjaxResult getSgjsSpecialMeasureList(@Validated(ValidationGroups.Select.class) SgjsSpecialMeasure sgjsSpecialMeasureParam) {
        startPage();
        List<SgjsSpecialMeasure> sgjsSpecialMeasureList = sgjsSpecialMeasureService.getSgjsSpecialMeasureList(sgjsSpecialMeasureParam);
        return getDataTableAjaxResult(sgjsSpecialMeasureList);
    }

    @PreAuthorize(hasPermi = "sgjsSpecialMeasure:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsSpecialMeasure(@Validated(ValidationGroups.Save.class) @RequestBody SgjsSpecialMeasure sgjsSpecialMeasureParam) {
        sgjsSpecialMeasureService.insertSgjsSpecialMeasure(sgjsSpecialMeasureParam);
        return AjaxResult.success(sgjsSpecialMeasureParam);
    }

    @PreAuthorize(hasPermi = "sgjsSpecialMeasure:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsSpecialMeasureList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsSpecialMeasure> sgjsSpecialMeasureListParam) {
        sgjsSpecialMeasureService.insertSgjsSpecialMeasureList(sgjsSpecialMeasureListParam);
        return AjaxResult.success(sgjsSpecialMeasureListParam);
    }

    @PreAuthorize(hasPermi = "sgjsSpecialMeasure:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsSpecialMeasure(@Validated(ValidationGroups.Update.class) @RequestBody SgjsSpecialMeasure sgjsSpecialMeasureParam) {
        return toAjax(sgjsSpecialMeasureService.updateSgjsSpecialMeasure(sgjsSpecialMeasureParam));
    }

    @PreAuthorize(hasPermi = "sgjsSpecialMeasure:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsSpecialMeasureList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsSpecialMeasure> sgjsSpecialMeasureListParam) {
        return toAjax(sgjsSpecialMeasureService.updateSgjsSpecialMeasureList(sgjsSpecialMeasureListParam));
    }

    @PreAuthorize(hasPermi = "sgjsSpecialMeasure:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsSpecialMeasure(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsSpecialMeasure sgjsSpecialMeasureParam) {
        return toAjax(sgjsSpecialMeasureService.deleteSgjsSpecialMeasure(sgjsSpecialMeasureParam));
    }

    @PreAuthorize(hasPermi = "sgjsSpecialMeasure:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsSpecialMeasureByPks(@PathVariable Long[] ids) {
        List<Long> sgjsSpecialMeasurePkList = Arrays.asList(ids);
        return toAjax(sgjsSpecialMeasureService.deleteSgjsSpecialMeasureByPks(sgjsSpecialMeasurePkList));
    }

    @PostMapping("/export")
    @PreAuthorize(hasPermi = "sgjsSpecialMeasure:export")
    public void export(HttpServletResponse response,@RequestBody SgjsSpecialMeasure sgjsSpecialMeasureParam) throws IOException {
        List<SgjsSpecialMeasure> sgjsSpecialMeasureList = sgjsSpecialMeasureService.getSgjsSpecialMeasureList(sgjsSpecialMeasureParam);
        ExcelUtils<SgjsSpecialMeasure> util = new ExcelUtils<>(SgjsSpecialMeasure.class);
        util.exportExcel(response, sgjsSpecialMeasureList, DateUtils.getDate());
    }
}
