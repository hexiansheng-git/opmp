package com.hhwy.pm.qqch.preparation.measureexp.plan.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.measureexp.plan.domain.QqchMeasureExpPlan;
import com.hhwy.pm.qqch.preparation.measureexp.plan.service.IQqchMeasureExpPlanService;
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
 * @author mls
 * @date 2023-07-25 18:01:32
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchMeasureExpPlan")
public class QqchMeasureExpPlanController extends BaseController{

    @Autowired
    private IQqchMeasureExpPlanService qqchMeasureExpPlanService;

                                                                                                                                                                                                                                                                                                                            

    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:list")
    @GetMapping
    public AjaxResult getQqchMeasureExpPlan(@Validated(ValidationGroups.Get.class) QqchMeasureExpPlan qqchMeasureExpPlanParam){
        QqchMeasureExpPlan qqchMeasureExpPlan =  qqchMeasureExpPlanService.getQqchMeasureExpPlan(qqchMeasureExpPlanParam);
        return AjaxResult.success(qqchMeasureExpPlan);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchMeasureExpPlanList(@Validated(ValidationGroups.Select.class) QqchMeasureExpPlan qqchMeasureExpPlanParam){
        startPage();
        List<QqchMeasureExpPlan> qqchMeasureExpPlanList = qqchMeasureExpPlanService.getQqchMeasureExpPlanList(qqchMeasureExpPlanParam);
        return getDataTableAjaxResult(qqchMeasureExpPlanList);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchMeasureExpPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchMeasureExpPlan qqchMeasureExpPlanParam){
        qqchMeasureExpPlanService.insertQqchMeasureExpPlan(qqchMeasureExpPlanParam);
        return AjaxResult.success(qqchMeasureExpPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchMeasureExpPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchMeasureExpPlan> qqchMeasureExpPlanListParam){
        qqchMeasureExpPlanService.insertQqchMeasureExpPlanList(qqchMeasureExpPlanListParam);
        return AjaxResult.success(qqchMeasureExpPlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchMeasureExpPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchMeasureExpPlan qqchMeasureExpPlanParam){
        return toAjax(qqchMeasureExpPlanService.updateQqchMeasureExpPlan(qqchMeasureExpPlanParam));
    }

            @PreAuthorize(hasPermi = "qqchMeasureExpPlan:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchMeasureExpPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchMeasureExpPlan> qqchMeasureExpPlanListParam){
            return toAjax(qqchMeasureExpPlanService.updateQqchMeasureExpPlanList(qqchMeasureExpPlanListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchMeasureExpPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchMeasureExpPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchMeasureExpPlan qqchMeasureExpPlanParam){
        return toAjax(qqchMeasureExpPlanService.deleteQqchMeasureExpPlan(qqchMeasureExpPlanParam));
    }

            @PreAuthorize(hasPermi = "qqchMeasureExpPlan:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchMeasureExpPlanByPks(@PathVariable Long[] ids){
            List<Long> qqchMeasureExpPlanPkList = Arrays.asList(ids);
            return toAjax(qqchMeasureExpPlanService.deleteQqchMeasureExpPlanByPks(qqchMeasureExpPlanPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchMeasureExpPlan qqchMeasureExpPlanParam) throws IOException {
        List<QqchMeasureExpPlan> qqchMeasureExpPlanList = qqchMeasureExpPlanService.getQqchMeasureExpPlanList(qqchMeasureExpPlanParam);
        ExcelUtils<QqchMeasureExpPlan> util = new ExcelUtils<>(QqchMeasureExpPlan.class);
        util.exportExcel(response, qqchMeasureExpPlanList, DateUtils.getDate());
    }
}
