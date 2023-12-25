package com.hhwy.sp.experiment.sgjsExperimentRecordInfo.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.domain.SgjsExperimentRecordInfo;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.service.ISgjsExperimentRecordInfoService;
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
 * @author lcf--设备实际进场记录
 * @date 2023-12-11 15:03:58
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsExperimentRecordInfo")
public class SgjsExperimentRecordInfoController extends BaseController{

    @Autowired
    private ISgjsExperimentRecordInfoService sgjsExperimentRecordInfoService;


//    @PreAuthorize(hasPermi = "sgjsExperimentRecordInfo:list")
    @GetMapping
    public AjaxResult getSgjsExperimentRecordInfo(@Validated(ValidationGroups.Get.class)  SgjsExperimentRecordInfo sgjsExperimentRecordInfoParam){
        SgjsExperimentRecordInfo sgjsExperimentRecordInfo =  sgjsExperimentRecordInfoService.getSgjsExperimentRecordInfo(sgjsExperimentRecordInfoParam);
        return AjaxResult.success(sgjsExperimentRecordInfo);
    }

    /**
     * 列表查询
     *
     * @param sgjsExperimentRecordInfoParam
     * @return
     */
//    @PreAuthorize(hasPermi = "sgjsExperimentRecordInfo:list")
    @GetMapping("/list")
    public AjaxResult getSgjsExperimentRecordInfoList(@Validated(ValidationGroups.Select.class) SgjsExperimentRecordInfo sgjsExperimentRecordInfoParam){
        startPage();
        List<SgjsExperimentRecordInfo> sgjsExperimentRecordInfoList = sgjsExperimentRecordInfoService.getSgjsExperimentRecordInfoList(sgjsExperimentRecordInfoParam);
        return getDataTableAjaxResult(sgjsExperimentRecordInfoList);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecordInfo:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsExperimentRecordInfo(@Validated(ValidationGroups.Save.class) @RequestBody SgjsExperimentRecordInfo sgjsExperimentRecordInfoParam){
        sgjsExperimentRecordInfoService.insertSgjsExperimentRecordInfo(sgjsExperimentRecordInfoParam);
        return AjaxResult.success(sgjsExperimentRecordInfoParam);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecordInfo:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsExperimentRecordInfoList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsExperimentRecordInfo> sgjsExperimentRecordInfoListParam){
        sgjsExperimentRecordInfoService.insertSgjsExperimentRecordInfoList(sgjsExperimentRecordInfoListParam);
        return AjaxResult.success(sgjsExperimentRecordInfoListParam);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecordInfo:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsExperimentRecordInfo(@Validated(ValidationGroups.Update.class) @RequestBody SgjsExperimentRecordInfo sgjsExperimentRecordInfoParam){
        return toAjax(sgjsExperimentRecordInfoService.updateSgjsExperimentRecordInfo(sgjsExperimentRecordInfoParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecordInfo:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsExperimentRecordInfoList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsExperimentRecordInfo> sgjsExperimentRecordInfoListParam){
        return toAjax(sgjsExperimentRecordInfoService.updateSgjsExperimentRecordInfoList(sgjsExperimentRecordInfoListParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecordInfo:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsExperimentRecordInfo(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsExperimentRecordInfo sgjsExperimentRecordInfoParam){
        return toAjax(sgjsExperimentRecordInfoService.deleteSgjsExperimentRecordInfo(sgjsExperimentRecordInfoParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecordInfo:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsExperimentRecordInfoByPks(@PathVariable Long[] ids){
        List<Long> sgjsExperimentRecordInfoPkList = Arrays.asList(ids);
        return toAjax(sgjsExperimentRecordInfoService.deleteSgjsExperimentRecordInfoByPks(sgjsExperimentRecordInfoPkList));
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody SgjsExperimentRecordInfo sgjsExperimentRecordInfoParam) throws IOException {
        List<SgjsExperimentRecordInfo> sgjsExperimentRecordInfoList = sgjsExperimentRecordInfoService.getSgjsExperimentRecordInfoList(sgjsExperimentRecordInfoParam);
        ExcelUtils<SgjsExperimentRecordInfo> util = new ExcelUtils<>(SgjsExperimentRecordInfo.class);
        util.exportExcel(response, sgjsExperimentRecordInfoList, DateUtils.getDate());
    }

    @PostMapping("/batchAddMap")
    public AjaxResult batchAddMap(@RequestBody Map<String,Object> map){
        int i=sgjsExperimentRecordInfoService.batchAddMap(map);
        if(i==-1){
            return AjaxResult.error("试验编码重复了。。。。。");
        }
        return AjaxResult.success(i);
    }
}
