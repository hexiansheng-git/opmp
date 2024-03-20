package com.hhwy.sp.buildSchemeManage.review.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeStaffOpinionRecord;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeStaffOpinionRecordService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:40:06
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsBuildSchemeStaffOpinionRecord")
public class SgjsBuildSchemeStaffOpinionRecordController extends BaseController {

    @Autowired
    private ISgjsBuildSchemeStaffOpinionRecordService sgjsBuildSchemeStaffOpinionRecordService;


    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinionRecord:list")
    @GetMapping
    public AjaxResult getSgjsBuildSchemeStaffOpinionRecord(@Validated(ValidationGroups.Get.class) SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecordParam) {
        SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord = sgjsBuildSchemeStaffOpinionRecordService.getSgjsBuildSchemeStaffOpinionRecord(sgjsBuildSchemeStaffOpinionRecordParam);
        return AjaxResult.success(sgjsBuildSchemeStaffOpinionRecord);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinionRecord:list")
    @GetMapping("/list")
    public AjaxResult getSgjsBuildSchemeStaffOpinionRecordList(@Validated(ValidationGroups.Select.class) SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecordParam) {
        startPage();
        List<SgjsBuildSchemeStaffOpinionRecord> sgjsBuildSchemeStaffOpinionRecordList = sgjsBuildSchemeStaffOpinionRecordService.getSgjsBuildSchemeStaffOpinionRecordList(sgjsBuildSchemeStaffOpinionRecordParam);
        return getDataTableAjaxResult(sgjsBuildSchemeStaffOpinionRecordList);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinionRecord:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsBuildSchemeStaffOpinionRecord(@Validated(ValidationGroups.Save.class) @RequestBody SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecordParam) {
        sgjsBuildSchemeStaffOpinionRecordService.insertSgjsBuildSchemeStaffOpinionRecord(sgjsBuildSchemeStaffOpinionRecordParam);
        return AjaxResult.success(sgjsBuildSchemeStaffOpinionRecordParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinionRecord:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsBuildSchemeStaffOpinionRecordList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsBuildSchemeStaffOpinionRecord> sgjsBuildSchemeStaffOpinionRecordListParam) {
        sgjsBuildSchemeStaffOpinionRecordService.insertSgjsBuildSchemeStaffOpinionRecordList(sgjsBuildSchemeStaffOpinionRecordListParam);
        return AjaxResult.success(sgjsBuildSchemeStaffOpinionRecordListParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinionRecord:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsBuildSchemeStaffOpinionRecord(@Validated(ValidationGroups.Update.class) @RequestBody SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecordParam) {
        return toAjax(sgjsBuildSchemeStaffOpinionRecordService.updateSgjsBuildSchemeStaffOpinionRecord(sgjsBuildSchemeStaffOpinionRecordParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinionRecord:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsBuildSchemeStaffOpinionRecordList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsBuildSchemeStaffOpinionRecord> sgjsBuildSchemeStaffOpinionRecordListParam) {
        return toAjax(sgjsBuildSchemeStaffOpinionRecordService.updateSgjsBuildSchemeStaffOpinionRecordList(sgjsBuildSchemeStaffOpinionRecordListParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinionRecord:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsBuildSchemeStaffOpinionRecord(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecordParam) {
        return toAjax(sgjsBuildSchemeStaffOpinionRecordService.deleteSgjsBuildSchemeStaffOpinionRecord(sgjsBuildSchemeStaffOpinionRecordParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinionRecord:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsBuildSchemeStaffOpinionRecordByPks(@PathVariable Long[] ids) {
        List<Long> sgjsBuildSchemeStaffOpinionRecordPkList = Arrays.asList(ids);
        return toAjax(sgjsBuildSchemeStaffOpinionRecordService.deleteSgjsBuildSchemeStaffOpinionRecordByPks(sgjsBuildSchemeStaffOpinionRecordPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecordParam) throws IOException {
        List<SgjsBuildSchemeStaffOpinionRecord> sgjsBuildSchemeStaffOpinionRecordList = sgjsBuildSchemeStaffOpinionRecordService.getSgjsBuildSchemeStaffOpinionRecordList(sgjsBuildSchemeStaffOpinionRecordParam);
        ExcelUtils<SgjsBuildSchemeStaffOpinionRecord> util = new ExcelUtils<>(SgjsBuildSchemeStaffOpinionRecord.class);
        util.exportExcel(response, sgjsBuildSchemeStaffOpinionRecordList, DateUtils.getDate());
    }
}
