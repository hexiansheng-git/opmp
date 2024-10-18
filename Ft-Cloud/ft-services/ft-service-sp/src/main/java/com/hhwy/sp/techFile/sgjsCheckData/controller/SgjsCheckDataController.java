package com.hhwy.sp.techFile.sgjsCheckData.controller;

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
import com.hhwy.sp.techFile.sgjsCheckData.service.ISgjsCheckDataService;
import com.hhwy.sp.techFile.sgjsCheckData.domain.SgjsCheckData;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;

/**
 * @author xuzl
 * @date 2024-10-18 15:52:00
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsCheckData")
public class SgjsCheckDataController extends BaseController {

    @Autowired
    private ISgjsCheckDataService sgjsCheckDataService;


    @GetMapping
    public AjaxResult getSgjsCheckData(@Validated(ValidationGroups.Get.class) @RequestBody SgjsCheckData sgjsCheckDataParam) {
        SgjsCheckData sgjsCheckData = sgjsCheckDataService.getSgjsCheckData(sgjsCheckDataParam);
        return AjaxResult.success(sgjsCheckData);
    }

    @GetMapping("/list")
    public AjaxResult getSgjsCheckDataList(@Validated(ValidationGroups.Select.class) @RequestBody SgjsCheckData sgjsCheckDataParam) {
        startPage();
        List<SgjsCheckData> sgjsCheckDataList = sgjsCheckDataService.getSgjsCheckDataList(sgjsCheckDataParam);
        return getDataTableAjaxResult(sgjsCheckDataList);
    }

    @PostMapping("/add")
    public AjaxResult insertSgjsCheckData(@Validated(ValidationGroups.Save.class) @RequestBody SgjsCheckData sgjsCheckDataParam) {
        sgjsCheckDataService.insertSgjsCheckData(sgjsCheckDataParam);
        return AjaxResult.success(sgjsCheckDataParam);
    }

    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsCheckDataList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsCheckData> sgjsCheckDataListParam) {
        sgjsCheckDataService.insertSgjsCheckDataList(sgjsCheckDataListParam);
        return AjaxResult.success(sgjsCheckDataListParam);
    }

    @PostMapping("/update")
    public AjaxResult updateSgjsCheckData(@Validated(ValidationGroups.Update.class) @RequestBody SgjsCheckData sgjsCheckDataParam) {
        return toAjax(sgjsCheckDataService.updateSgjsCheckData(sgjsCheckDataParam));
    }

    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsCheckDataList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsCheckData> sgjsCheckDataListParam) {
        return toAjax(sgjsCheckDataService.updateSgjsCheckDataList(sgjsCheckDataListParam));
    }

    @PostMapping("/delete")
    public AjaxResult deleteSgjsCheckData(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsCheckData sgjsCheckDataParam) {
        return toAjax(sgjsCheckDataService.deleteSgjsCheckData(sgjsCheckDataParam));
    }

    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsCheckDataByPks(@PathVariable Long[] ids) {
        List<Long> sgjsCheckDataPkList = Arrays.asList(ids);
        return toAjax(sgjsCheckDataService.deleteSgjsCheckDataByPks(sgjsCheckDataPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsCheckData sgjsCheckDataParam) throws IOException {
        List<SgjsCheckData> sgjsCheckDataList = sgjsCheckDataService.getSgjsCheckDataList(sgjsCheckDataParam);
        ExcelUtils<SgjsCheckData> util = new ExcelUtils<>(SgjsCheckData.class);
        util.exportExcel(response, sgjsCheckDataList, DateUtils.getDate());
    }
}
