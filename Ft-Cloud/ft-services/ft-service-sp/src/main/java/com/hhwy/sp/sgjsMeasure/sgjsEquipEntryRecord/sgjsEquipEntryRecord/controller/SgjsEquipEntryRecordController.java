package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.controller;

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
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.service.ISgjsEquipEntryRecordService;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.domain.SgjsEquipEntryRecord;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * 测量管理--测试设备进场记录
 *
 * @date 2023-12-08 10:47:00
 * @remark
 * @author lcf
 */
@Validated
@RestController
@RequestMapping("/sgjsEquipEntryRecord")
public class SgjsEquipEntryRecordController extends BaseController{

    @Autowired
    private ISgjsEquipEntryRecordService sgjsEquipEntryRecordService;

                                                                                                                                                                                                                                                                                                                                                                                        

    @PreAuthorize(hasPermi = "sgjsEquipEntryRecord:list")
    @GetMapping
    public AjaxResult getSgjsEquipEntryRecord(@Validated(ValidationGroups.Get.class)  SgjsEquipEntryRecord sgjsEquipEntryRecordParam){
        SgjsEquipEntryRecord sgjsEquipEntryRecord =  sgjsEquipEntryRecordService.getSgjsEquipEntryRecord(sgjsEquipEntryRecordParam);
        return AjaxResult.success(sgjsEquipEntryRecord);
    }

    /**
     * 列表查询
     *
     * @param sgjsEquipEntryRecordParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsEquipEntryRecord:list")
    @GetMapping("/list")
    public AjaxResult getSgjsEquipEntryRecordList(@Validated(ValidationGroups.Select.class) SgjsEquipEntryRecord sgjsEquipEntryRecordParam){
        startPage();
        List<SgjsEquipEntryRecord> sgjsEquipEntryRecordList = sgjsEquipEntryRecordService.getSgjsEquipEntryRecordList(sgjsEquipEntryRecordParam);
        return getDataTableAjaxResult(sgjsEquipEntryRecordList);
    }

    @PreAuthorize(hasPermi = "sgjsEquipEntryRecord:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsEquipEntryRecord(@Validated(ValidationGroups.Save.class) @RequestBody SgjsEquipEntryRecord sgjsEquipEntryRecordParam){
        sgjsEquipEntryRecordService.insertSgjsEquipEntryRecord(sgjsEquipEntryRecordParam);
        return AjaxResult.success(sgjsEquipEntryRecordParam);
    }

    @PreAuthorize(hasPermi = "sgjsEquipEntryRecord:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsEquipEntryRecordList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsEquipEntryRecord> sgjsEquipEntryRecordListParam){
        sgjsEquipEntryRecordService.insertSgjsEquipEntryRecordList(sgjsEquipEntryRecordListParam);
        return AjaxResult.success(sgjsEquipEntryRecordListParam);
    }

    @PreAuthorize(hasPermi = "sgjsEquipEntryRecord:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsEquipEntryRecord(@Validated(ValidationGroups.Update.class) @RequestBody SgjsEquipEntryRecord sgjsEquipEntryRecordParam){
        return toAjax(sgjsEquipEntryRecordService.updateSgjsEquipEntryRecord(sgjsEquipEntryRecordParam));
    }

            @PreAuthorize(hasPermi = "sgjsEquipEntryRecord:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateSgjsEquipEntryRecordList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsEquipEntryRecord> sgjsEquipEntryRecordListParam){
            return toAjax(sgjsEquipEntryRecordService.updateSgjsEquipEntryRecordList(sgjsEquipEntryRecordListParam));
        }
    
    @PreAuthorize(hasPermi = "sgjsEquipEntryRecord:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsEquipEntryRecord(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsEquipEntryRecord sgjsEquipEntryRecordParam){
        return toAjax(sgjsEquipEntryRecordService.deleteSgjsEquipEntryRecord(sgjsEquipEntryRecordParam));
    }

            @PreAuthorize(hasPermi = "sgjsEquipEntryRecord:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteSgjsEquipEntryRecordByPks(@PathVariable Long[] ids){
            List<Long> sgjsEquipEntryRecordPkList = Arrays.asList(ids);
            return toAjax(sgjsEquipEntryRecordService.deleteSgjsEquipEntryRecordByPks(sgjsEquipEntryRecordPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsEquipEntryRecord sgjsEquipEntryRecordParam) throws IOException {
        List<SgjsEquipEntryRecord> sgjsEquipEntryRecordList = sgjsEquipEntryRecordService.getSgjsEquipEntryRecordList(sgjsEquipEntryRecordParam);
        ExcelUtils<SgjsEquipEntryRecord> util = new ExcelUtils<>(SgjsEquipEntryRecord.class);
        util.exportExcel(response, sgjsEquipEntryRecordList, DateUtils.getDate());
    }
}
