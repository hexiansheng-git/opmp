package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.service.ISgjsEquipEntryRecordInfoDetailService;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.domain.SgjsEquipEntryRecordInfoDetail;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:49:49
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsEquipEntryRecordInfoDetail")
public class SgjsEquipEntryRecordInfoDetailController extends BaseController{

    @Autowired
    private ISgjsEquipEntryRecordInfoDetailService sgjsEquipEntryRecordInfoDetailService;


    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfoDetail:list")
    @GetMapping
    public AjaxResult getSgjsEquipEntryRecordInfoDetail(@Validated(ValidationGroups.Get.class)  SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetailParam){
        SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail =  sgjsEquipEntryRecordInfoDetailService.getSgjsEquipEntryRecordInfoDetail(sgjsEquipEntryRecordInfoDetailParam);
        return AjaxResult.success(sgjsEquipEntryRecordInfoDetail);
    }

    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfoDetail:list")
    @GetMapping("/list")
    public AjaxResult getSgjsEquipEntryRecordInfoDetailList(@Validated(ValidationGroups.Select.class) SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetailParam){
        startPage();
        List<SgjsEquipEntryRecordInfoDetail> sgjsEquipEntryRecordInfoDetailList = sgjsEquipEntryRecordInfoDetailService.getSgjsEquipEntryRecordInfoDetailList(sgjsEquipEntryRecordInfoDetailParam);
        return getDataTableAjaxResult(sgjsEquipEntryRecordInfoDetailList);
    }

    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfoDetail:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsEquipEntryRecordInfoDetail(@Validated(ValidationGroups.Save.class) @RequestBody SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetailParam){
        sgjsEquipEntryRecordInfoDetailService.insertSgjsEquipEntryRecordInfoDetail(sgjsEquipEntryRecordInfoDetailParam);
        return AjaxResult.success(sgjsEquipEntryRecordInfoDetailParam);
    }

    /**
     * 批量新增
     *
     * @param sgjsEquipEntryRecordInfoDetailListParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfoDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsEquipEntryRecordInfoDetailList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsEquipEntryRecordInfoDetail> sgjsEquipEntryRecordInfoDetailListParam){
        sgjsEquipEntryRecordInfoDetailService.insertSgjsEquipEntryRecordInfoDetailList(sgjsEquipEntryRecordInfoDetailListParam);
        return AjaxResult.success();
    }
    /**
     * 批量新增
     *
     * @param map
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfoDetail:batchAddMap")
    @PostMapping("/batchAddMap")
    public AjaxResult batchAddMap(@Validated(ValidationGroups.Save.class) @RequestBody Map<String,Object> map){
        sgjsEquipEntryRecordInfoDetailService.batchAddMap(map);
        return AjaxResult.success();
    }



    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfoDetail:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsEquipEntryRecordInfoDetail(@Validated(ValidationGroups.Update.class) @RequestBody SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetailParam){
        return toAjax(sgjsEquipEntryRecordInfoDetailService.updateSgjsEquipEntryRecordInfoDetail(sgjsEquipEntryRecordInfoDetailParam));
    }

    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfoDetail:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsEquipEntryRecordInfoDetailList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsEquipEntryRecordInfoDetail> sgjsEquipEntryRecordInfoDetailListParam){
        return toAjax(sgjsEquipEntryRecordInfoDetailService.updateSgjsEquipEntryRecordInfoDetailList(sgjsEquipEntryRecordInfoDetailListParam));
    }

    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfoDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsEquipEntryRecordInfoDetail(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetailParam){
        return toAjax(sgjsEquipEntryRecordInfoDetailService.deleteSgjsEquipEntryRecordInfoDetail(sgjsEquipEntryRecordInfoDetailParam));
    }

    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfoDetail:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsEquipEntryRecordInfoDetailByPks(@PathVariable Long[] ids){
        List<Long> sgjsEquipEntryRecordInfoDetailPkList = Arrays.asList(ids);
        return toAjax(sgjsEquipEntryRecordInfoDetailService.deleteSgjsEquipEntryRecordInfoDetailByPks(sgjsEquipEntryRecordInfoDetailPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetailParam) throws IOException {
        List<SgjsEquipEntryRecordInfoDetail> sgjsEquipEntryRecordInfoDetailList = sgjsEquipEntryRecordInfoDetailService.getSgjsEquipEntryRecordInfoDetailList(sgjsEquipEntryRecordInfoDetailParam);
        ExcelUtils<SgjsEquipEntryRecordInfoDetail> util = new ExcelUtils<>(SgjsEquipEntryRecordInfoDetail.class);
        util.exportExcel(response, sgjsEquipEntryRecordInfoDetailList, DateUtils.getDate());
    }
}
