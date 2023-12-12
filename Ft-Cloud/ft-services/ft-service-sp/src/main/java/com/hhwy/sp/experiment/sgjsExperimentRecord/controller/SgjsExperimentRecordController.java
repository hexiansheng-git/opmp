package com.hhwy.sp.experiment.sgjsExperimentRecord.controller;

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
import com.hhwy.sp.experiment.sgjsExperimentRecord.service.ISgjsExperimentRecordService;
import com.hhwy.sp.experiment.sgjsExperimentRecord.domain.SgjsExperimentRecord;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author lcf--试验设备进场记录
 * @date 2023-12-11 15:03:30
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsExperimentRecord")
public class SgjsExperimentRecordController extends BaseController{

    @Autowired
    private ISgjsExperimentRecordService sgjsExperimentRecordService;



    @PreAuthorize(hasPermi = "sgjsExperimentRecord:list")
    @GetMapping
    public AjaxResult getSgjsExperimentRecord(@Validated(ValidationGroups.Get.class)  SgjsExperimentRecord sgjsExperimentRecordParam){
        SgjsExperimentRecord sgjsExperimentRecord =  sgjsExperimentRecordService.getSgjsExperimentRecord(sgjsExperimentRecordParam);
        return AjaxResult.success(sgjsExperimentRecord);
    }

    /**
     * 列表查询
     *
     * @param sgjsExperimentRecordParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsExperimentRecord:list")
    @GetMapping("/list")
    public AjaxResult getSgjsExperimentRecordList(@Validated(ValidationGroups.Select.class) SgjsExperimentRecord sgjsExperimentRecordParam){
        startPage();
        List<SgjsExperimentRecord> sgjsExperimentRecordList = sgjsExperimentRecordService.getSgjsExperimentRecordList(sgjsExperimentRecordParam);
        return getDataTableAjaxResult(sgjsExperimentRecordList);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecord:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsExperimentRecord(@Validated(ValidationGroups.Save.class) @RequestBody SgjsExperimentRecord sgjsExperimentRecordParam){
        sgjsExperimentRecordService.insertSgjsExperimentRecord(sgjsExperimentRecordParam);
        return AjaxResult.success(sgjsExperimentRecordParam);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecord:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsExperimentRecordList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsExperimentRecord> sgjsExperimentRecordListParam){
        sgjsExperimentRecordService.insertSgjsExperimentRecordList(sgjsExperimentRecordListParam);
        return AjaxResult.success(sgjsExperimentRecordListParam);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecord:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsExperimentRecord(@Validated(ValidationGroups.Update.class) @RequestBody SgjsExperimentRecord sgjsExperimentRecordParam){
        return toAjax(sgjsExperimentRecordService.updateSgjsExperimentRecord(sgjsExperimentRecordParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecord:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsExperimentRecordList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsExperimentRecord> sgjsExperimentRecordListParam){
        return toAjax(sgjsExperimentRecordService.updateSgjsExperimentRecordList(sgjsExperimentRecordListParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecord:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsExperimentRecord(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsExperimentRecord sgjsExperimentRecordParam){
        return toAjax(sgjsExperimentRecordService.deleteSgjsExperimentRecord(sgjsExperimentRecordParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecord:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsExperimentRecordByPks(@PathVariable Long[] ids){
        List<Long> sgjsExperimentRecordPkList = Arrays.asList(ids);
        return toAjax(sgjsExperimentRecordService.deleteSgjsExperimentRecordByPks(sgjsExperimentRecordPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsExperimentRecord sgjsExperimentRecordParam) throws IOException {
        List<SgjsExperimentRecord> sgjsExperimentRecordList = sgjsExperimentRecordService.getSgjsExperimentRecordList(sgjsExperimentRecordParam);
        ExcelUtils<SgjsExperimentRecord> util = new ExcelUtils<>(SgjsExperimentRecord.class);
        util.exportExcel(response, sgjsExperimentRecordList, DateUtils.getDate());
    }

    @PostMapping("/sync")
    public AjaxResult sync(){
        AjaxResult ajaxResult =sgjsExperimentRecordService.sync();
        return ajaxResult;
    }
}
