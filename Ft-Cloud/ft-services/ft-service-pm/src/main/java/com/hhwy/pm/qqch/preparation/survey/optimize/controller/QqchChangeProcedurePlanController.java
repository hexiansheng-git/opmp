package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchChangeProcedurePlan;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchChangeProcedurePlanService;
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
 * @author han
 * @date 2023-07-07 18:35:34
 * @remark 变更程序策划
 */
@Validated
@RestController
@RequestMapping("/qqchChangeProcedurePlan")
public class QqchChangeProcedurePlanController extends BaseController{

    @Autowired
    private IQqchChangeProcedurePlanService qqchChangeProcedurePlanService;

                                                                                                                                                                                                                                                                                                                

    @PreAuthorize(hasPermi = "qqchChangeProcedurePlan:list")
    @GetMapping
    public AjaxResult getQqchChangeProcedurePlan(@Validated(ValidationGroups.Get.class) @RequestBody QqchChangeProcedurePlan qqchChangeProcedurePlanParam){
        QqchChangeProcedurePlan qqchChangeProcedurePlan =  qqchChangeProcedurePlanService.getQqchChangeProcedurePlan(qqchChangeProcedurePlanParam);
        return AjaxResult.success(qqchChangeProcedurePlan);
    }

    @PreAuthorize(hasPermi = "qqchChangeProcedurePlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchChangeProcedurePlanList(@Validated(ValidationGroups.Select.class) @RequestBody QqchChangeProcedurePlan qqchChangeProcedurePlanParam){
        startPage();
        List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList = qqchChangeProcedurePlanService.getQqchChangeProcedurePlanList(qqchChangeProcedurePlanParam);
        return getDataTableAjaxResult(qqchChangeProcedurePlanList);
    }

    @PreAuthorize(hasPermi = "qqchChangeProcedurePlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchChangeProcedurePlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchChangeProcedurePlan qqchChangeProcedurePlanParam){
        qqchChangeProcedurePlanService.insertQqchChangeProcedurePlan(qqchChangeProcedurePlanParam);
        return AjaxResult.success(qqchChangeProcedurePlanParam);
    }

    @PreAuthorize(hasPermi = "qqchChangeProcedurePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchChangeProcedurePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchChangeProcedurePlan> qqchChangeProcedurePlanListParam){
        qqchChangeProcedurePlanService.insertQqchChangeProcedurePlanList(qqchChangeProcedurePlanListParam);
        return AjaxResult.success(qqchChangeProcedurePlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchChangeProcedurePlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchChangeProcedurePlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchChangeProcedurePlan qqchChangeProcedurePlanParam){
        return toAjax(qqchChangeProcedurePlanService.updateQqchChangeProcedurePlan(qqchChangeProcedurePlanParam));
    }

            @PreAuthorize(hasPermi = "qqchChangeProcedurePlan:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchChangeProcedurePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchChangeProcedurePlan> qqchChangeProcedurePlanListParam){
            return toAjax(qqchChangeProcedurePlanService.updateQqchChangeProcedurePlanList(qqchChangeProcedurePlanListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchChangeProcedurePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchChangeProcedurePlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchChangeProcedurePlan qqchChangeProcedurePlanParam){
        return toAjax(qqchChangeProcedurePlanService.deleteQqchChangeProcedurePlan(qqchChangeProcedurePlanParam));
    }

            @PreAuthorize(hasPermi = "qqchChangeProcedurePlan:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchChangeProcedurePlanByPks(@PathVariable Long[] ids){
            List<Long> qqchChangeProcedurePlanPkList = Arrays.asList(ids);
            return toAjax(qqchChangeProcedurePlanService.deleteQqchChangeProcedurePlanByPks(qqchChangeProcedurePlanPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchChangeProcedurePlan qqchChangeProcedurePlanParam) throws IOException {
        List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList = qqchChangeProcedurePlanService.getQqchChangeProcedurePlanList(qqchChangeProcedurePlanParam);
        ExcelUtils<QqchChangeProcedurePlan> util = new ExcelUtils<>(QqchChangeProcedurePlan.class);
        util.exportExcel(response, qqchChangeProcedurePlanList, DateUtils.getDate());
    }
}
