package com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.controller;

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
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.service.ISgjsExperimentRecordInfoDetailService;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfoDetail.domain.SgjsExperimentRecordInfoDetail;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author lcf--自检自校表记录
 * @date 2023-12-11 15:04:20
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/sgjsExperimentRecordInfoDetail")
public class SgjsExperimentRecordInfoDetailController extends BaseController{

    @Autowired
    private ISgjsExperimentRecordInfoDetailService sgjsExperimentRecordInfoDetailService;

                                                                                                                                                                                                                                                                                                    

//    @PreAuthorize(hasPermi = "sgjsExperimentRecordInfoDetail:list")
    @GetMapping
    public AjaxResult getSgjsExperimentRecordInfoDetail(@Validated(ValidationGroups.Get.class)  SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetailParam){
        SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetail =  sgjsExperimentRecordInfoDetailService.getSgjsExperimentRecordInfoDetail(sgjsExperimentRecordInfoDetailParam);
        return AjaxResult.success(sgjsExperimentRecordInfoDetail);
    }

//    @PreAuthorize(hasPermi = "sgjsExperimentRecordInfoDetail:list")
    @GetMapping("/list")
    public AjaxResult getSgjsExperimentRecordInfoDetailList(@Validated(ValidationGroups.Select.class) SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetailParam){
        startPage();
        List<SgjsExperimentRecordInfoDetail> sgjsExperimentRecordInfoDetailList = sgjsExperimentRecordInfoDetailService.getSgjsExperimentRecordInfoDetailList(sgjsExperimentRecordInfoDetailParam);
        return getDataTableAjaxResult(sgjsExperimentRecordInfoDetailList);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecordInfoDetail:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsExperimentRecordInfoDetail(@Validated(ValidationGroups.Save.class) @RequestBody SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetailParam){
        sgjsExperimentRecordInfoDetailService.insertSgjsExperimentRecordInfoDetail(sgjsExperimentRecordInfoDetailParam);
        return AjaxResult.success(sgjsExperimentRecordInfoDetailParam);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecordInfoDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsExperimentRecordInfoDetailList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsExperimentRecordInfoDetail> sgjsExperimentRecordInfoDetailListParam){
        sgjsExperimentRecordInfoDetailService.insertSgjsExperimentRecordInfoDetailList(sgjsExperimentRecordInfoDetailListParam);
        return AjaxResult.success(sgjsExperimentRecordInfoDetailListParam);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentRecordInfoDetail:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsExperimentRecordInfoDetail(@Validated(ValidationGroups.Update.class) @RequestBody SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetailParam){
        return toAjax(sgjsExperimentRecordInfoDetailService.updateSgjsExperimentRecordInfoDetail(sgjsExperimentRecordInfoDetailParam));
    }

            @PreAuthorize(hasPermi = "sgjsExperimentRecordInfoDetail:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateSgjsExperimentRecordInfoDetailList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsExperimentRecordInfoDetail> sgjsExperimentRecordInfoDetailListParam){
            return toAjax(sgjsExperimentRecordInfoDetailService.updateSgjsExperimentRecordInfoDetailList(sgjsExperimentRecordInfoDetailListParam));
        }
    
    @PreAuthorize(hasPermi = "sgjsExperimentRecordInfoDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsExperimentRecordInfoDetail(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetailParam){
        return toAjax(sgjsExperimentRecordInfoDetailService.deleteSgjsExperimentRecordInfoDetail(sgjsExperimentRecordInfoDetailParam));
    }

            @PreAuthorize(hasPermi = "sgjsExperimentRecordInfoDetail:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteSgjsExperimentRecordInfoDetailByPks(@PathVariable Long[] ids){
            List<Long> sgjsExperimentRecordInfoDetailPkList = Arrays.asList(ids);
            return toAjax(sgjsExperimentRecordInfoDetailService.deleteSgjsExperimentRecordInfoDetailByPks(sgjsExperimentRecordInfoDetailPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsExperimentRecordInfoDetail sgjsExperimentRecordInfoDetailParam) throws IOException {
        List<SgjsExperimentRecordInfoDetail> sgjsExperimentRecordInfoDetailList = sgjsExperimentRecordInfoDetailService.getSgjsExperimentRecordInfoDetailList(sgjsExperimentRecordInfoDetailParam);
        ExcelUtils<SgjsExperimentRecordInfoDetail> util = new ExcelUtils<>(SgjsExperimentRecordInfoDetail.class);
        util.exportExcel(response, sgjsExperimentRecordInfoDetailList, DateUtils.getDate());
    }
}
