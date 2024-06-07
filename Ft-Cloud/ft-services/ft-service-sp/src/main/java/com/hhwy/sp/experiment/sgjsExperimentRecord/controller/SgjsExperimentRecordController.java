package com.hhwy.sp.experiment.sgjsExperimentRecord.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.experiment.sgjsExperimentRecord.domain.SgjsExperimentRecord;
import com.hhwy.sp.experiment.sgjsExperimentRecord.service.ISgjsExperimentRecordService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    //@PreAuthorize(hasPermi = "sgjsExperimentRecord:list")
    @GetMapping("/list")
    public AjaxResult getSgjsExperimentRecordList(@Validated(ValidationGroups.Select.class) SgjsExperimentRecord sgjsExperimentRecordParam){
        startPage();
        List<SgjsExperimentRecord> sgjsExperimentRecordList = sgjsExperimentRecordService.getSgjsExperimentRecordList(sgjsExperimentRecordParam);
        return getDataTableAjaxResult(sgjsExperimentRecordList);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecord:add")
    @PostMapping("/add")
    @CustomLogger(title = "施工技术-试验管理-设备台账及检验记录", name = "设备台账及检验记录" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertSgjsExperimentRecord(@Validated(ValidationGroups.Save.class) @RequestBody SgjsExperimentRecord sgjsExperimentRecordParam){
        sgjsExperimentRecordService.insertSgjsExperimentRecord(sgjsExperimentRecordParam);
        return AjaxResult.success(sgjsExperimentRecordParam);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecord:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "施工技术-试验管理-设备台账及检验记录", name = "设备台账及检验记录" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertSgjsExperimentRecordList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsExperimentRecord> sgjsExperimentRecordListParam){
        sgjsExperimentRecordService.insertSgjsExperimentRecordList(sgjsExperimentRecordListParam);
        return AjaxResult.success(sgjsExperimentRecordListParam);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecord:update")
    @PostMapping("/update")
    @CustomLogger(title = "施工技术-试验管理-设备台账及检验记录", name = "设备台账及检验记录" ,businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateSgjsExperimentRecord(@Validated(ValidationGroups.Update.class) @RequestBody SgjsExperimentRecord sgjsExperimentRecordParam){
        return toAjax(sgjsExperimentRecordService.updateSgjsExperimentRecord(sgjsExperimentRecordParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecord:update")
    @PostMapping("/batchUpdate")
    @CustomLogger(title = "施工技术-试验管理-设备台账及检验记录", name = "设备台账及检验记录" ,businessType = CustomBusinessType.UPDATE)
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
    @CustomLogger(title = "施工技术-试验管理-设备台账及检验记录", name = "设备台账及检验记录" ,businessType = CustomBusinessType.EXPORT)
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

    /**
     * 同步物设项目设备进场记录
     *
     * @return
     */
    @PostMapping("/syncWuShe")
    public AjaxResult syncWuShe(@RequestBody List<Map> map){
        //传参校验
        for (Map info:map) {
            Object materialCode = info.get("materialCode");
            Object source = info.get("source");
            if(null==source){
                return AjaxResult.error("设备编码"+materialCode.toString()+"的来源不能为空!!!!");
            }
        }
        AjaxResult ajaxResult = sgjsExperimentRecordService.syncWuShe(map);
        return ajaxResult;
    }

    /**
     *
     * 施工技术--设备台账及检验报告 预警消息
     *
     * @date2024-04-01
     * @return
     */
    @GetMapping("/experimentRecordJob")
    public AjaxResult experimentRecordJob(){
        AjaxResult ajaxResult =sgjsExperimentRecordService.experimentRecordJob();
        return ajaxResult;
    }
}
