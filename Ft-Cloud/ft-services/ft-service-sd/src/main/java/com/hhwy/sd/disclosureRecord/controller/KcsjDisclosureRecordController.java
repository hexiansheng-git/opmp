package com.hhwy.sd.disclosureRecord.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.disclosureRecord.domain.KcsjDisclosureRecord;
import com.hhwy.sd.disclosureRecord.service.IKcsjDisclosureRecordService;
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
 * @date 2023-12-18 11:15:48
 * @remark
 */
@Validated
@RestController
@RequestMapping("/kcsjDisclosureRecord")
public class KcsjDisclosureRecordController extends BaseController {

    @Autowired
    private IKcsjDisclosureRecordService kcsjDisclosureRecordService;


    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:list")
    @GetMapping
    public AjaxResult getKcsjDisclosureRecord(@Validated(ValidationGroups.Get.class) KcsjDisclosureRecord kcsjDisclosureRecordParam) {
        KcsjDisclosureRecord kcsjDisclosureRecord = kcsjDisclosureRecordService.getKcsjDisclosureRecord(kcsjDisclosureRecordParam);
        return AjaxResult.success(kcsjDisclosureRecord);
    }

    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:list")
    @GetMapping("/list")
    public AjaxResult getKcsjDisclosureRecordList(@Validated(ValidationGroups.Select.class) KcsjDisclosureRecord kcsjDisclosureRecordParam) {
        startPage();
        List<KcsjDisclosureRecord> kcsjDisclosureRecordList = kcsjDisclosureRecordService.getKcsjDisclosureRecordList(kcsjDisclosureRecordParam);
        return getDataTableAjaxResult(kcsjDisclosureRecordList);
    }

    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjDisclosureRecord(@Validated(ValidationGroups.Save.class) @RequestBody KcsjDisclosureRecord kcsjDisclosureRecordParam) {
        kcsjDisclosureRecordService.insertKcsjDisclosureRecord(kcsjDisclosureRecordParam);
        return AjaxResult.success(kcsjDisclosureRecordParam);
    }

    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjDisclosureRecordList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjDisclosureRecord> kcsjDisclosureRecordListParam) {
        kcsjDisclosureRecordService.insertKcsjDisclosureRecordList(kcsjDisclosureRecordListParam);
        return AjaxResult.success(kcsjDisclosureRecordListParam);
    }

    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjDisclosureRecord(@Validated(ValidationGroups.Update.class) @RequestBody KcsjDisclosureRecord kcsjDisclosureRecordParam) {
        return toAjax(kcsjDisclosureRecordService.updateKcsjDisclosureRecord(kcsjDisclosureRecordParam));
    }

    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjDisclosureRecordList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjDisclosureRecord> kcsjDisclosureRecordListParam) {
        return toAjax(kcsjDisclosureRecordService.updateKcsjDisclosureRecordList(kcsjDisclosureRecordListParam));
    }

    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjDisclosureRecord(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjDisclosureRecord kcsjDisclosureRecordParam) {
        return toAjax(kcsjDisclosureRecordService.deleteKcsjDisclosureRecord(kcsjDisclosureRecordParam));
    }

    @PreAuthorize(hasPermi = "kcsjDisclosureRecord:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjDisclosureRecordByPks(@PathVariable Long[] ids) {
        List<Long> kcsjDisclosureRecordPkList = Arrays.asList(ids);
        return toAjax(kcsjDisclosureRecordService.deleteKcsjDisclosureRecordByPks(kcsjDisclosureRecordPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjDisclosureRecord kcsjDisclosureRecordParam) throws IOException {
        List<KcsjDisclosureRecord> kcsjDisclosureRecordList = kcsjDisclosureRecordService.getKcsjDisclosureRecordList(kcsjDisclosureRecordParam);
        ExcelUtils<KcsjDisclosureRecord> util = new ExcelUtils<>(KcsjDisclosureRecord.class);
        util.exportExcel(response, kcsjDisclosureRecordList, DateUtils.getDate());
    }
}
