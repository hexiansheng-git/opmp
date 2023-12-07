package com.hhwy.sp.sgjsPlanMeasure.sgjsPlanMeasureManage.controller;

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
import com.hhwy.sp.sgjsPlanMeasure.sgjsPlanMeasureManage.service.ISgjsPlanMeasureManageService;
import com.hhwy.sp.sgjsPlanMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManage;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author zmh
 * @date 2023-12-07 18:13:51
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/sgjsPlanMeasureManage")
public class SgjsPlanMeasureManageController extends BaseController{

    @Autowired
    private ISgjsPlanMeasureManageService sgjsPlanMeasureManageService;

                                                                                                                                                                                                                                                                                                                

    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:list")
    @GetMapping
    public AjaxResult getSgjsPlanMeasureManage(@Validated(ValidationGroups.Get.class)  SgjsPlanMeasureManage sgjsPlanMeasureManageParam){
        SgjsPlanMeasureManage sgjsPlanMeasureManage =  sgjsPlanMeasureManageService.getSgjsPlanMeasureManage(sgjsPlanMeasureManageParam);
        return AjaxResult.success(sgjsPlanMeasureManage);
    }

    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:list")
    @GetMapping("/list")
    public AjaxResult getSgjsPlanMeasureManageList(@Validated(ValidationGroups.Select.class) SgjsPlanMeasureManage sgjsPlanMeasureManageParam){
        startPage();
        List<SgjsPlanMeasureManage> sgjsPlanMeasureManageList = sgjsPlanMeasureManageService.getSgjsPlanMeasureManageList(sgjsPlanMeasureManageParam);
        return getDataTableAjaxResult(sgjsPlanMeasureManageList);
    }

    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsPlanMeasureManage(@Validated(ValidationGroups.Save.class) @RequestBody SgjsPlanMeasureManage sgjsPlanMeasureManageParam){
        sgjsPlanMeasureManageService.insertSgjsPlanMeasureManage(sgjsPlanMeasureManageParam);
        return AjaxResult.success(sgjsPlanMeasureManageParam);
    }

    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsPlanMeasureManageList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsPlanMeasureManage> sgjsPlanMeasureManageListParam){
        sgjsPlanMeasureManageService.insertSgjsPlanMeasureManageList(sgjsPlanMeasureManageListParam);
        return AjaxResult.success(sgjsPlanMeasureManageListParam);
    }

    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsPlanMeasureManage(@Validated(ValidationGroups.Update.class) @RequestBody SgjsPlanMeasureManage sgjsPlanMeasureManageParam){
        return toAjax(sgjsPlanMeasureManageService.updateSgjsPlanMeasureManage(sgjsPlanMeasureManageParam));
    }

            @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateSgjsPlanMeasureManageList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsPlanMeasureManage> sgjsPlanMeasureManageListParam){
            return toAjax(sgjsPlanMeasureManageService.updateSgjsPlanMeasureManageList(sgjsPlanMeasureManageListParam));
        }
    
    @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsPlanMeasureManage(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsPlanMeasureManage sgjsPlanMeasureManageParam){
        return toAjax(sgjsPlanMeasureManageService.deleteSgjsPlanMeasureManage(sgjsPlanMeasureManageParam));
    }

            @PreAuthorize(hasPermi = "sgjsPlanMeasureManage:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteSgjsPlanMeasureManageByPks(@PathVariable Long[] ids){
            List<Long> sgjsPlanMeasureManagePkList = Arrays.asList(ids);
            return toAjax(sgjsPlanMeasureManageService.deleteSgjsPlanMeasureManageByPks(sgjsPlanMeasureManagePkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsPlanMeasureManage sgjsPlanMeasureManageParam) throws IOException {
        List<SgjsPlanMeasureManage> sgjsPlanMeasureManageList = sgjsPlanMeasureManageService.getSgjsPlanMeasureManageList(sgjsPlanMeasureManageParam);
        ExcelUtils<SgjsPlanMeasureManage> util = new ExcelUtils<>(SgjsPlanMeasureManage.class);
        util.exportExcel(response, sgjsPlanMeasureManageList, DateUtils.getDate());
    }
}
