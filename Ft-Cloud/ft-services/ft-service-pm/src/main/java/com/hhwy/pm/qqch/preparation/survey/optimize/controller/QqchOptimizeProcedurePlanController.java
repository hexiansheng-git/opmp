package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeProcedurePlan;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchOptimizeProcedurePlanService;
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
 * @date 2023-07-07 18:35:53
 * @remark 优化程序策划
 */
@Validated
@RestController
@RequestMapping("/qqchOptimizeProcedurePlan")
public class QqchOptimizeProcedurePlanController extends BaseController {

    @Autowired
    private IQqchOptimizeProcedurePlanService qqchOptimizeProcedurePlanService;


    @PreAuthorize(hasPermi = "qqchOptimizeProcedurePlan:list")
    @GetMapping
    public AjaxResult getQqchOptimizeProcedurePlan(@Validated(ValidationGroups.Get.class) @RequestBody QqchOptimizeProcedurePlan qqchOptimizeProcedurePlanParam) {
        QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan = qqchOptimizeProcedurePlanService.getQqchOptimizeProcedurePlan(qqchOptimizeProcedurePlanParam);
        return AjaxResult.success(qqchOptimizeProcedurePlan);
    }

    /**
     * 优化程序策划台账
     * @return
     */
    @PreAuthorize(hasPermi = "qqchOptimizeProcedurePlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchOptimizeProcedurePlanList() {
        List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList = qqchOptimizeProcedurePlanService.getQqchOptimizeProcedurePlanList();
        return AjaxResult.success(qqchOptimizeProcedurePlanList);
    }

    /**
     * 批量编辑（新增和修改）
     * @param qqchOptimizeProcedurePlanListParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchOptimizeProcedurePlan:update")
    @PostMapping("/batchEdit")
    public AjaxResult editQqchOptimizeProcedurePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanListParam) {
        qqchOptimizeProcedurePlanService.editQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanListParam);
        return AjaxResult.success(qqchOptimizeProcedurePlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchOptimizeProcedurePlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchOptimizeProcedurePlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchOptimizeProcedurePlan qqchOptimizeProcedurePlanParam) {
        qqchOptimizeProcedurePlanService.insertQqchOptimizeProcedurePlan(qqchOptimizeProcedurePlanParam);
        return AjaxResult.success(qqchOptimizeProcedurePlanParam);
    }

    @PreAuthorize(hasPermi = "qqchOptimizeProcedurePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchOptimizeProcedurePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanListParam) {
        qqchOptimizeProcedurePlanService.insertQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanListParam);
        return AjaxResult.success(qqchOptimizeProcedurePlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchOptimizeProcedurePlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchOptimizeProcedurePlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchOptimizeProcedurePlan qqchOptimizeProcedurePlanParam) {
        return toAjax(qqchOptimizeProcedurePlanService.updateQqchOptimizeProcedurePlan(qqchOptimizeProcedurePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchOptimizeProcedurePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchOptimizeProcedurePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanListParam) {
        return toAjax(qqchOptimizeProcedurePlanService.updateQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchOptimizeProcedurePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchOptimizeProcedurePlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchOptimizeProcedurePlan qqchOptimizeProcedurePlanParam) {
        return toAjax(qqchOptimizeProcedurePlanService.deleteQqchOptimizeProcedurePlan(qqchOptimizeProcedurePlanParam));
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PreAuthorize(hasPermi = "qqchOptimizeProcedurePlan:remove")
    @PostMapping("/remove/{ids}")
    public AjaxResult deleteQqchOptimizeProcedurePlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchOptimizeProcedurePlanPkList = Arrays.asList(ids);
        return toAjax(qqchOptimizeProcedurePlanService.deleteQqchOptimizeProcedurePlanByPks(qqchOptimizeProcedurePlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchOptimizeProcedurePlan qqchOptimizeProcedurePlanParam) throws IOException {
        List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList = qqchOptimizeProcedurePlanService.getQqchOptimizeProcedurePlanList();
        ExcelUtils<QqchOptimizeProcedurePlan> util = new ExcelUtils<>(QqchOptimizeProcedurePlan.class);
        util.exportExcel(response, qqchOptimizeProcedurePlanList, DateUtils.getDate());
    }
}
