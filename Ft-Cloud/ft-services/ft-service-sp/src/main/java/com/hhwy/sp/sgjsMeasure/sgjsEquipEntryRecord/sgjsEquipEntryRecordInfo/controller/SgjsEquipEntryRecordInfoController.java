package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.controller;

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
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.service.ISgjsEquipEntryRecordInfoService;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.domain.SgjsEquipEntryRecordInfo;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:49:36
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsEquipEntryRecordInfo")
public class SgjsEquipEntryRecordInfoController extends BaseController{

    @Autowired
    private ISgjsEquipEntryRecordInfoService sgjsEquipEntryRecordInfoService;


    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfo:list")
    @GetMapping
    public AjaxResult getSgjsEquipEntryRecordInfo(@Validated(ValidationGroups.Get.class)  SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfoParam){
        SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo =  sgjsEquipEntryRecordInfoService.getSgjsEquipEntryRecordInfo(sgjsEquipEntryRecordInfoParam);
        return AjaxResult.success(sgjsEquipEntryRecordInfo);
    }

    /**
     * 查询
     *
     * @param sgjsEquipEntryRecordInfoParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfo:list")
    @GetMapping("/list")
    public AjaxResult getSgjsEquipEntryRecordInfoList(@Validated(ValidationGroups.Select.class) SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfoParam){
        startPage();
        List<SgjsEquipEntryRecordInfo> sgjsEquipEntryRecordInfoList = sgjsEquipEntryRecordInfoService.getSgjsEquipEntryRecordInfoList(sgjsEquipEntryRecordInfoParam);
        return getDataTableAjaxResult(sgjsEquipEntryRecordInfoList);
    }

    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfo:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsEquipEntryRecordInfo(@Validated(ValidationGroups.Save.class) @RequestBody SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfoParam){
        sgjsEquipEntryRecordInfoService.insertSgjsEquipEntryRecordInfo(sgjsEquipEntryRecordInfoParam);
        return AjaxResult.success(sgjsEquipEntryRecordInfoParam);
    }

    /**
     * 批量新增
     *
     * @param sgjsEquipEntryRecordInfoListParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfo:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsEquipEntryRecordInfoList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsEquipEntryRecordInfo> sgjsEquipEntryRecordInfoListParam){
        sgjsEquipEntryRecordInfoService.insertSgjsEquipEntryRecordInfoList(sgjsEquipEntryRecordInfoListParam);
        return AjaxResult.success();
    }


    /**
     * 批量新增
     *
     * @param map
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfo:addMap")
    @PostMapping("/batchAddMap")
    public AjaxResult batchAddMap(@RequestBody Map<String,Object> map){
        sgjsEquipEntryRecordInfoService.batchAddMap(map);
        return AjaxResult.success();
    }


    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfo:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsEquipEntryRecordInfo(@Validated(ValidationGroups.Update.class) @RequestBody SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfoParam){
        return toAjax(sgjsEquipEntryRecordInfoService.updateSgjsEquipEntryRecordInfo(sgjsEquipEntryRecordInfoParam));
    }

    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfo:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsEquipEntryRecordInfoList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsEquipEntryRecordInfo> sgjsEquipEntryRecordInfoListParam){
        return toAjax(sgjsEquipEntryRecordInfoService.updateSgjsEquipEntryRecordInfoList(sgjsEquipEntryRecordInfoListParam));
    }

    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfo:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsEquipEntryRecordInfo(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfoParam){
        return toAjax(sgjsEquipEntryRecordInfoService.deleteSgjsEquipEntryRecordInfo(sgjsEquipEntryRecordInfoParam));
    }

    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfo:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsEquipEntryRecordInfoByPks(@PathVariable Long[] ids){
        List<Long> sgjsEquipEntryRecordInfoPkList = Arrays.asList(ids);
        return toAjax(sgjsEquipEntryRecordInfoService.deleteSgjsEquipEntryRecordInfoByPks(sgjsEquipEntryRecordInfoPkList));
    }

    /**
     * 导出
     *
     * @param response
     * @param sgjsEquipEntryRecordInfoParam
     * @throws IOException
     */
    @PostMapping("/export")
    @PreAuthorize(hasPermi = "sgjsEquipEntryRecordInfo:export")
    public void export(HttpServletResponse response, @RequestBody SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfoParam) throws IOException {
        List<SgjsEquipEntryRecordInfo> sgjsEquipEntryRecordInfoList = sgjsEquipEntryRecordInfoService.getSgjsEquipEntryRecordInfoList(sgjsEquipEntryRecordInfoParam);
        ExcelUtils<SgjsEquipEntryRecordInfo> util = new ExcelUtils<>(SgjsEquipEntryRecordInfo.class);
        util.exportExcel(response, sgjsEquipEntryRecordInfoList, DateUtils.getDate());
    }
}
