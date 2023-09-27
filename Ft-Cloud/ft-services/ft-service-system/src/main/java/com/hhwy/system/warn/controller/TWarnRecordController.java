package com.hhwy.system.warn.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.domain.base.system.warn.TWarnRecord;
import com.hhwy.system.warn.service.ITWarnRecordService;
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
 * @date 2023-09-26 17:52:28
 * @remark
 */
@Validated
@RestController
@RequestMapping("/tWarnRecord")
public class TWarnRecordController extends BaseController {

    @Autowired
    private ITWarnRecordService tWarnRecordService;


    @PreAuthorize(hasPermi = "tWarnRecord:list")
    @GetMapping
    public AjaxResult getTWarnRecord(@Validated(ValidationGroups.Get.class) TWarnRecord tWarnRecordParam) {
        TWarnRecord tWarnRecord = tWarnRecordService.getTWarnRecord(tWarnRecordParam);
        return AjaxResult.success(tWarnRecord);
    }

    @PreAuthorize(hasPermi = "tWarnRecord:list")
    @GetMapping("/list")
    public AjaxResult getTWarnRecordList(@Validated(ValidationGroups.Select.class) TWarnRecord tWarnRecordParam) {
        startPage();
        List<TWarnRecord> tWarnRecordList = tWarnRecordService.getTWarnRecordList(tWarnRecordParam);
        return getDataTableAjaxResult(tWarnRecordList);
    }

    @PreAuthorize(hasPermi = "tWarnRecord:add")
    @PostMapping("/add")
    public AjaxResult insertTWarnRecord(@Validated(ValidationGroups.Save.class) @RequestBody TWarnRecord tWarnRecordParam) {
        tWarnRecordService.insertTWarnRecord(tWarnRecordParam);
        return AjaxResult.success(tWarnRecordParam);
    }

    @PreAuthorize(hasPermi = "tWarnRecord:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertTWarnRecordList(@Validated(ValidationGroups.Save.class) @RequestBody List<TWarnRecord> tWarnRecordListParam) {
        tWarnRecordService.insertTWarnRecordList(tWarnRecordListParam);
        return AjaxResult.success(tWarnRecordListParam);
    }

    @PreAuthorize(hasPermi = "tWarnRecord:update")
    @PostMapping("/update")
    public AjaxResult updateTWarnRecord(@Validated(ValidationGroups.Update.class) @RequestBody TWarnRecord tWarnRecordParam) {
        return toAjax(tWarnRecordService.updateTWarnRecord(tWarnRecordParam));
    }

    @PreAuthorize(hasPermi = "tWarnRecord:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateTWarnRecordList(@Validated(ValidationGroups.Update.class) @RequestBody List<TWarnRecord> tWarnRecordListParam) {
        return toAjax(tWarnRecordService.updateTWarnRecordList(tWarnRecordListParam));
    }

    @PreAuthorize(hasPermi = "tWarnRecord:remove")
    @PostMapping("/delete")
    public AjaxResult deleteTWarnRecord(@Validated(ValidationGroups.Delete.class) @RequestBody TWarnRecord tWarnRecordParam) {
        return toAjax(tWarnRecordService.deleteTWarnRecord(tWarnRecordParam));
    }

    @PreAuthorize(hasPermi = "tWarnRecord:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteTWarnRecordByPks(@PathVariable Long[] ids) {
        List<Long> tWarnRecordPkList = Arrays.asList(ids);
        return toAjax(tWarnRecordService.deleteTWarnRecordByPks(tWarnRecordPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, TWarnRecord tWarnRecordParam) throws IOException {
        List<TWarnRecord> tWarnRecordList = tWarnRecordService.getTWarnRecordList(tWarnRecordParam);
        ExcelUtils<TWarnRecord> util = new ExcelUtils<>(TWarnRecord.class);
        util.exportExcel(response, tWarnRecordList, DateUtils.getDate());
    }
}
