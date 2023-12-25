package com.hhwy.sp.experiment.sgjsExperimentTotalPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.experiment.sgjsExperimentTotalPlan.domain.SgjsExperimentTotalPlan;
import com.hhwy.sp.experiment.sgjsExperimentTotalPlan.service.ISgjsExperimentTotalPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author lcf--试验总体计划
 * @date 2023-12-11 10:00:11
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsExperimentTotalPlan")
public class SgjsExperimentTotalPlanController extends BaseController{

    @Autowired
    private ISgjsExperimentTotalPlanService sgjsExperimentTotalPlanService;


    @PreAuthorize(hasPermi = "sgjsExperimentTotalPlan:list")
    @GetMapping
    public AjaxResult getSgjsExperimentTotalPlan(@Validated(ValidationGroups.Get.class)  SgjsExperimentTotalPlan sgjsExperimentTotalPlanParam){
        SgjsExperimentTotalPlan sgjsExperimentTotalPlan =  sgjsExperimentTotalPlanService.getSgjsExperimentTotalPlan(sgjsExperimentTotalPlanParam);
        return AjaxResult.success(sgjsExperimentTotalPlan);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentTotalPlan:list")
    @GetMapping("/list")
    public AjaxResult getSgjsExperimentTotalPlanList(@Validated(ValidationGroups.Select.class) SgjsExperimentTotalPlan sgjsExperimentTotalPlanParam){
        startPage();
        List<SgjsExperimentTotalPlan> sgjsExperimentTotalPlanList = sgjsExperimentTotalPlanService.getSgjsExperimentTotalPlanList(sgjsExperimentTotalPlanParam);
        return getDataTableAjaxResult(sgjsExperimentTotalPlanList);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentTotalPlan:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsExperimentTotalPlan(@Validated(ValidationGroups.Save.class) @RequestBody SgjsExperimentTotalPlan sgjsExperimentTotalPlanParam){
        sgjsExperimentTotalPlanService.insertSgjsExperimentTotalPlan(sgjsExperimentTotalPlanParam);
        return AjaxResult.success(sgjsExperimentTotalPlanParam);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentTotalPlan:batchAdd")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsExperimentTotalPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsExperimentTotalPlan> sgjsExperimentTotalPlanListParam){
        sgjsExperimentTotalPlanService.insertSgjsExperimentTotalPlanList(sgjsExperimentTotalPlanListParam);
        return AjaxResult.success(sgjsExperimentTotalPlanListParam);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentTotalPlan:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsExperimentTotalPlan(@Validated(ValidationGroups.Update.class) @RequestBody SgjsExperimentTotalPlan sgjsExperimentTotalPlanParam){
        return toAjax(sgjsExperimentTotalPlanService.updateSgjsExperimentTotalPlan(sgjsExperimentTotalPlanParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperimentTotalPlan:batchUpdate")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsExperimentTotalPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsExperimentTotalPlan> sgjsExperimentTotalPlanListParam){
        return toAjax(sgjsExperimentTotalPlanService.updateSgjsExperimentTotalPlanList(sgjsExperimentTotalPlanListParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperimentTotalPlan:delete")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsExperimentTotalPlan(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsExperimentTotalPlan sgjsExperimentTotalPlanParam){
        return toAjax(sgjsExperimentTotalPlanService.deleteSgjsExperimentTotalPlan(sgjsExperimentTotalPlanParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperimentTotalPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsExperimentTotalPlanByPks(@PathVariable Long[] ids){
        List<Long> sgjsExperimentTotalPlanPkList = Arrays.asList(ids);
        return toAjax(sgjsExperimentTotalPlanService.deleteSgjsExperimentTotalPlanByPks(sgjsExperimentTotalPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsExperimentTotalPlan sgjsExperimentTotalPlanParam) throws IOException {
        List<SgjsExperimentTotalPlan> sgjsExperimentTotalPlanList = sgjsExperimentTotalPlanService.getSgjsExperimentTotalPlanList(sgjsExperimentTotalPlanParam);
        ExcelUtils<SgjsExperimentTotalPlan> util = new ExcelUtils<>(SgjsExperimentTotalPlan.class);
        util.exportExcel(response, sgjsExperimentTotalPlanList, DateUtils.getDate());
    }

    /**
     * 根据项目id查询项目信息
     *
     * @param
     * @return
     */
    @GetMapping("/selectPrjById")
    @PreAuthorize(hasPermi = "sgjsExperimentTotalPlan:selectPrjById")
    public AjaxResult selectPrjById(){
        SgjsExperimentTotalPlan info =sgjsExperimentTotalPlanService.selectDetailInfo();
        return AjaxResult.success(info);
    }
}
