package com.hhwy.sp.buildSchemeManage.review.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewOpinionRecord;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeReviewOpinionRecordService;
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
 * @date 2024-03-20 09:39:48
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsBuildSchemeReviewOpinionRecord")
public class SgjsBuildSchemeReviewOpinionRecordController extends BaseController {

    @Autowired
    private ISgjsBuildSchemeReviewOpinionRecordService sgjsBuildSchemeReviewOpinionRecordService;


    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinionRecord:list")
    @GetMapping
    public AjaxResult getSgjsBuildSchemeReviewOpinionRecord(@Validated(ValidationGroups.Get.class) SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecordParam) {
        SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord = sgjsBuildSchemeReviewOpinionRecordService.getSgjsBuildSchemeReviewOpinionRecord(sgjsBuildSchemeReviewOpinionRecordParam);
        return AjaxResult.success(sgjsBuildSchemeReviewOpinionRecord);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinionRecord:list")
    @GetMapping("/list")
    public AjaxResult getSgjsBuildSchemeReviewOpinionRecordList(@Validated(ValidationGroups.Select.class) SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecordParam) {
        startPage();
        List<SgjsBuildSchemeReviewOpinionRecord> sgjsBuildSchemeReviewOpinionRecordList = sgjsBuildSchemeReviewOpinionRecordService.getSgjsBuildSchemeReviewOpinionRecordList(sgjsBuildSchemeReviewOpinionRecordParam);
        return getDataTableAjaxResult(sgjsBuildSchemeReviewOpinionRecordList);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinionRecord:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsBuildSchemeReviewOpinionRecord(@Validated(ValidationGroups.Save.class) @RequestBody SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecordParam) {
        sgjsBuildSchemeReviewOpinionRecordService.insertSgjsBuildSchemeReviewOpinionRecord(sgjsBuildSchemeReviewOpinionRecordParam);
        return AjaxResult.success(sgjsBuildSchemeReviewOpinionRecordParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinionRecord:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsBuildSchemeReviewOpinionRecordList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsBuildSchemeReviewOpinionRecord> sgjsBuildSchemeReviewOpinionRecordListParam) {
        sgjsBuildSchemeReviewOpinionRecordService.insertSgjsBuildSchemeReviewOpinionRecordList(sgjsBuildSchemeReviewOpinionRecordListParam);
        return AjaxResult.success(sgjsBuildSchemeReviewOpinionRecordListParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinionRecord:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsBuildSchemeReviewOpinionRecord(@Validated(ValidationGroups.Update.class) @RequestBody SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecordParam) {
        return toAjax(sgjsBuildSchemeReviewOpinionRecordService.updateSgjsBuildSchemeReviewOpinionRecord(sgjsBuildSchemeReviewOpinionRecordParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinionRecord:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsBuildSchemeReviewOpinionRecordList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsBuildSchemeReviewOpinionRecord> sgjsBuildSchemeReviewOpinionRecordListParam) {
        return toAjax(sgjsBuildSchemeReviewOpinionRecordService.updateSgjsBuildSchemeReviewOpinionRecordList(sgjsBuildSchemeReviewOpinionRecordListParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinionRecord:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsBuildSchemeReviewOpinionRecord(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecordParam) {
        return toAjax(sgjsBuildSchemeReviewOpinionRecordService.deleteSgjsBuildSchemeReviewOpinionRecord(sgjsBuildSchemeReviewOpinionRecordParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinionRecord:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsBuildSchemeReviewOpinionRecordByPks(@PathVariable Long[] ids) {
        List<Long> sgjsBuildSchemeReviewOpinionRecordPkList = Arrays.asList(ids);
        return toAjax(sgjsBuildSchemeReviewOpinionRecordService.deleteSgjsBuildSchemeReviewOpinionRecordByPks(sgjsBuildSchemeReviewOpinionRecordPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecordParam) throws IOException {
        List<SgjsBuildSchemeReviewOpinionRecord> sgjsBuildSchemeReviewOpinionRecordList = sgjsBuildSchemeReviewOpinionRecordService.getSgjsBuildSchemeReviewOpinionRecordList(sgjsBuildSchemeReviewOpinionRecordParam);
        ExcelUtils<SgjsBuildSchemeReviewOpinionRecord> util = new ExcelUtils<>(SgjsBuildSchemeReviewOpinionRecord.class);
        util.exportExcel(response, sgjsBuildSchemeReviewOpinionRecordList, DateUtils.getDate());
    }
}
