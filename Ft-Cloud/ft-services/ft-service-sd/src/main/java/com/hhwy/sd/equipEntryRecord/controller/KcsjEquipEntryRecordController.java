package com.hhwy.sd.equipEntryRecord.controller;

import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecord;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecordVo;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sd.equipEntryRecord.service.IKcsjEquipEntryRecordService;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author zmh
 * @date 2023-12-14 11:08:08
 * @remark
 */
@Validated
@RestController
@RequestMapping("/kcsjEquipEntryRecord")
public class KcsjEquipEntryRecordController extends BaseController {

    @Autowired
    private IKcsjEquipEntryRecordService kcsjEquipEntryRecordService;


    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:list")
    @GetMapping
    public AjaxResult getKcsjEquipEntryRecord(@Validated(ValidationGroups.Get.class) KcsjEquipEntryRecord kcsjEquipEntryRecordParam) {
        KcsjEquipEntryRecord kcsjEquipEntryRecord = kcsjEquipEntryRecordService.getKcsjEquipEntryRecord(kcsjEquipEntryRecordParam);
        return AjaxResult.success(kcsjEquipEntryRecord);
    }

    /**
     * 台账页查询
     * @param kcsjEquipEntryRecordParam
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:list")
    @GetMapping("/list")
    public AjaxResult getKcsjEquipEntryRecordList(@Validated(ValidationGroups.Select.class) KcsjEquipEntryRecord kcsjEquipEntryRecordParam) {
        KcsjEquipEntryRecordVo kcsjEquipEntryRecordVo = kcsjEquipEntryRecordService.getKcsjEquipEntryRecordList(kcsjEquipEntryRecordParam);
        return AjaxResult.success(kcsjEquipEntryRecordVo);
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjEquipEntryRecord(@Validated(ValidationGroups.Save.class) @RequestBody KcsjEquipEntryRecord kcsjEquipEntryRecordParam) {
        kcsjEquipEntryRecordService.insertKcsjEquipEntryRecord(kcsjEquipEntryRecordParam);
        return AjaxResult.success(kcsjEquipEntryRecordParam);
    }

    /**
     * 批量保存
     * @param kcsjEquipEntryRecordVo
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjEquipEntryRecordList(@Validated(ValidationGroups.Save.class) @RequestBody KcsjEquipEntryRecordVo kcsjEquipEntryRecordVo) {
        AjaxResult ajaxResul =  kcsjEquipEntryRecordService.insertKcsjEquipEntryRecordList(kcsjEquipEntryRecordVo);
        return AjaxResult.success(ajaxResul);
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjEquipEntryRecord(@Validated(ValidationGroups.Update.class) @RequestBody KcsjEquipEntryRecord kcsjEquipEntryRecordParam) {
        return toAjax(kcsjEquipEntryRecordService.updateKcsjEquipEntryRecord(kcsjEquipEntryRecordParam));
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjEquipEntryRecordList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjEquipEntryRecord> kcsjEquipEntryRecordListParam) {
        return toAjax(kcsjEquipEntryRecordService.updateKcsjEquipEntryRecordList(kcsjEquipEntryRecordListParam));
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjEquipEntryRecord(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjEquipEntryRecord kcsjEquipEntryRecordParam) {
        return toAjax(kcsjEquipEntryRecordService.deleteKcsjEquipEntryRecord(kcsjEquipEntryRecordParam));
    }

    @PreAuthorize(hasPermi = "kcsjEquipEntryRecord:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjEquipEntryRecordByPks(@PathVariable Long[] ids) {
        List<Long> kcsjEquipEntryRecordPkList = Arrays.asList(ids);
        return toAjax(kcsjEquipEntryRecordService.deleteKcsjEquipEntryRecordByPks(kcsjEquipEntryRecordPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjEquipEntryRecord kcsjEquipEntryRecordParam) throws IOException {
        KcsjEquipEntryRecordVo kcsjEquipEntryRecordVo = kcsjEquipEntryRecordService.getKcsjEquipEntryRecordList(kcsjEquipEntryRecordParam);
        ExcelUtils<KcsjEquipEntryRecord> util = new ExcelUtils<>(KcsjEquipEntryRecord.class);
        util.exportExcel(response, kcsjEquipEntryRecordVo.getTreeList(), DateUtils.getDate());
    }
}
