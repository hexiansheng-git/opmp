package com.hhwy.pm.qqch.qqchWorkPlan.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.Map;

import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
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
 * @author hwj
 * @date 2023-07-12 15:30:52
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchWorkPlan")
public class QqchWorkPlanController extends BaseController {

    @Autowired
    private IQqchWorkPlanService qqchWorkPlanService;


    @GetMapping
    public AjaxResult getQqchWorkPlan(@Validated(ValidationGroups.Get.class) QqchWorkPlan qqchWorkPlanParam) {
        QqchWorkPlan qqchWorkPlan = qqchWorkPlanService.getQqchWorkPlan(qqchWorkPlanParam);
        return AjaxResult.success(qqchWorkPlan);
    }

    @GetMapping("/baseInfo")
    public AjaxResult baseInfo(@RequestParam Map<String, String> map) {
        return AjaxResult.success(qqchWorkPlanService.baseInfo(map));
    }

    @PreAuthorize(hasPermi = "qqchWorkPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchWorkPlanList(@Validated(ValidationGroups.Select.class) QqchWorkPlan qqchWorkPlanParam) {
        startPage();
        List<QqchWorkPlan> qqchWorkPlanList = qqchWorkPlanService.getQqchWorkPlanList(qqchWorkPlanParam);
        return getDataTableAjaxResult(qqchWorkPlanList);
    }

    @PreAuthorize(hasPermi = "qqchWorkPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchWorkPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchWorkPlan qqchWorkPlanParam) {
        qqchWorkPlanService.insertQqchWorkPlan(qqchWorkPlanParam);
        return AjaxResult.success(qqchWorkPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchWorkPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchWorkPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchWorkPlan> qqchWorkPlanListParam) {
        qqchWorkPlanService.insertQqchWorkPlanList(qqchWorkPlanListParam);
        return AjaxResult.success(qqchWorkPlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchWorkPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchWorkPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchWorkPlan qqchWorkPlanParam) {
        return toAjax(qqchWorkPlanService.updateQqchWorkPlan(qqchWorkPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchWorkPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchWorkPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchWorkPlan> qqchWorkPlanListParam) {
        return toAjax(qqchWorkPlanService.updateQqchWorkPlanList(qqchWorkPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchWorkPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchWorkPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchWorkPlan qqchWorkPlanParam) {
        return toAjax(qqchWorkPlanService.deleteQqchWorkPlan(qqchWorkPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchWorkPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchWorkPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchWorkPlanPkList = Arrays.asList(ids);
        return toAjax(qqchWorkPlanService.deleteQqchWorkPlanByPks(qqchWorkPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchWorkPlan qqchWorkPlanParam) throws IOException {
        List<QqchWorkPlan> qqchWorkPlanList = qqchWorkPlanService.getQqchWorkPlanList(qqchWorkPlanParam);
        ExcelUtils<QqchWorkPlan> util = new ExcelUtils<>(QqchWorkPlan.class);
        util.exportExcel(response, qqchWorkPlanList, DateUtils.getDate());
    }
}
