package com.hhwy.pm.jdgl.diff.analysis.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglWarnRecord;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglWarnRecordService;
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
 * @date 2023-09-01 13:29:05
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglWarnRecord")
public class JdglWarnRecordController extends BaseController {

    @Autowired
    private IJdglWarnRecordService jdglWarnRecordService;


    @PreAuthorize(hasPermi = "jdglWarnRecord:list")
    @GetMapping
    public AjaxResult getJdglWarnRecord(@Validated(ValidationGroups.Get.class) JdglWarnRecord jdglWarnRecordParam) {
        JdglWarnRecord jdglWarnRecord = jdglWarnRecordService.getJdglWarnRecord(jdglWarnRecordParam);
        return AjaxResult.success(jdglWarnRecord);
    }


    /**
     * 获取预警信息记录
     * @param jdglWarnRecord
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getJdglWarnRecordList(@Validated(ValidationGroups.Select.class) JdglWarnRecord jdglWarnRecord) {
        List<JdglWarnRecord> jdglWarnRecordList = jdglWarnRecordService.getJdglWarnRecordList(jdglWarnRecord);
        return AjaxResult.success(jdglWarnRecordList);
    }

    @PreAuthorize(hasPermi = "jdglWarnRecord:add")
    @PostMapping("/add")
    public AjaxResult insertJdglWarnRecord(@Validated(ValidationGroups.Save.class) @RequestBody JdglWarnRecord jdglWarnRecordParam) {
        jdglWarnRecordService.insertJdglWarnRecord(jdglWarnRecordParam);
        return AjaxResult.success(jdglWarnRecordParam);
    }

    @PreAuthorize(hasPermi = "jdglWarnRecord:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglWarnRecordList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglWarnRecord> jdglWarnRecordListParam) {
        jdglWarnRecordService.insertJdglWarnRecordList(jdglWarnRecordListParam);
        return AjaxResult.success(jdglWarnRecordListParam);
    }

    @PreAuthorize(hasPermi = "jdglWarnRecord:update")
    @PostMapping("/update")
    public AjaxResult updateJdglWarnRecord(@Validated(ValidationGroups.Update.class) @RequestBody JdglWarnRecord jdglWarnRecordParam) {
        return toAjax(jdglWarnRecordService.updateJdglWarnRecord(jdglWarnRecordParam));
    }

    @PreAuthorize(hasPermi = "jdglWarnRecord:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglWarnRecordList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglWarnRecord> jdglWarnRecordListParam) {
        return toAjax(jdglWarnRecordService.updateJdglWarnRecordList(jdglWarnRecordListParam));
    }

    @PreAuthorize(hasPermi = "jdglWarnRecord:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglWarnRecord(@Validated(ValidationGroups.Delete.class) @RequestBody JdglWarnRecord jdglWarnRecordParam) {
        return toAjax(jdglWarnRecordService.deleteJdglWarnRecord(jdglWarnRecordParam));
    }

    @PreAuthorize(hasPermi = "jdglWarnRecord:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglWarnRecordByPks(@PathVariable Long[] ids) {
        List<Long> jdglWarnRecordPkList = Arrays.asList(ids);
        return toAjax(jdglWarnRecordService.deleteJdglWarnRecordByPks(jdglWarnRecordPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglWarnRecord jdglWarnRecordParam) throws IOException {
        List<JdglWarnRecord> jdglWarnRecordList = jdglWarnRecordService.getJdglWarnRecordList(jdglWarnRecordParam);
        ExcelUtils<JdglWarnRecord> util = new ExcelUtils<>(JdglWarnRecord.class);
        util.exportExcel(response, jdglWarnRecordList, DateUtils.getDate());
    }
}
