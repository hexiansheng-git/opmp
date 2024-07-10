package com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.domain.SgjsPaperScoreRecord;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.service.ISgjsPaperScoreRecordService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author fsd
 * @date 2024-07-10 16:38:37
 * @remark 论文评分记录
 */
@Validated
@RestController
@RequestMapping("/sgjsPaperScoreRecord")
public class SgjsPaperScoreRecordController extends BaseController{

    @Autowired
    private ISgjsPaperScoreRecordService sgjsPaperScoreRecordService;

                                                                                                                                                                                                                                                                                                                                                                                                            

    @PreAuthorize(hasPermi = "sgjsPaperScoreRecord:list")
    @GetMapping
    public AjaxResult getSgjsPaperScoreRecord(@Validated(ValidationGroups.Get.class) SgjsPaperScoreRecord sgjsPaperScoreRecordParam){
        SgjsPaperScoreRecord sgjsPaperScoreRecord =  sgjsPaperScoreRecordService.getSgjsPaperScoreRecord(sgjsPaperScoreRecordParam);
        return AjaxResult.success(sgjsPaperScoreRecord);
    }

    @PreAuthorize(hasPermi = "sgjsPaperScoreRecord:list")
    @GetMapping("/list")
    public AjaxResult getSgjsPaperScoreRecordList(@Validated(ValidationGroups.Select.class) SgjsPaperScoreRecord sgjsPaperScoreRecordParam){
        startPage();
        List<SgjsPaperScoreRecord> sgjsPaperScoreRecordList = sgjsPaperScoreRecordService.getSgjsPaperScoreRecordList(sgjsPaperScoreRecordParam);
        return getDataTableAjaxResult(sgjsPaperScoreRecordList);
    }

    @PreAuthorize(hasPermi = "sgjsPaperScoreRecord:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsPaperScoreRecord(@Validated(ValidationGroups.Save.class) @RequestBody SgjsPaperScoreRecord sgjsPaperScoreRecordParam){
        sgjsPaperScoreRecordService.insertSgjsPaperScoreRecord(sgjsPaperScoreRecordParam);
        return AjaxResult.success(sgjsPaperScoreRecordParam);
    }

    @PreAuthorize(hasPermi = "sgjsPaperScoreRecord:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsPaperScoreRecordList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsPaperScoreRecord> sgjsPaperScoreRecordListParam){
        sgjsPaperScoreRecordService.insertSgjsPaperScoreRecordList(sgjsPaperScoreRecordListParam);
        return AjaxResult.success(sgjsPaperScoreRecordListParam);
    }

    @PreAuthorize(hasPermi = "sgjsPaperScoreRecord:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsPaperScoreRecord(@Validated(ValidationGroups.Update.class) @RequestBody SgjsPaperScoreRecord sgjsPaperScoreRecordParam){
        return toAjax(sgjsPaperScoreRecordService.updateSgjsPaperScoreRecord(sgjsPaperScoreRecordParam));
    }

            @PreAuthorize(hasPermi = "sgjsPaperScoreRecord:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateSgjsPaperScoreRecordList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsPaperScoreRecord> sgjsPaperScoreRecordListParam){
            return toAjax(sgjsPaperScoreRecordService.updateSgjsPaperScoreRecordList(sgjsPaperScoreRecordListParam));
        }
    
    @PreAuthorize(hasPermi = "sgjsPaperScoreRecord:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsPaperScoreRecord(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsPaperScoreRecord sgjsPaperScoreRecordParam){
        return toAjax(sgjsPaperScoreRecordService.deleteSgjsPaperScoreRecord(sgjsPaperScoreRecordParam));
    }

            @PreAuthorize(hasPermi = "sgjsPaperScoreRecord:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteSgjsPaperScoreRecordByPks(@PathVariable Long[] ids){
            List<Long> sgjsPaperScoreRecordPkList = Arrays.asList(ids);
            return toAjax(sgjsPaperScoreRecordService.deleteSgjsPaperScoreRecordByPks(sgjsPaperScoreRecordPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsPaperScoreRecord sgjsPaperScoreRecordParam) throws IOException {
        List<SgjsPaperScoreRecord> sgjsPaperScoreRecordList = sgjsPaperScoreRecordService.getSgjsPaperScoreRecordList(sgjsPaperScoreRecordParam);
        ExcelUtils<SgjsPaperScoreRecord> util = new ExcelUtils<>(SgjsPaperScoreRecord.class);
        util.exportExcel(response, sgjsPaperScoreRecordList, DateUtils.getDate());
    }
}
