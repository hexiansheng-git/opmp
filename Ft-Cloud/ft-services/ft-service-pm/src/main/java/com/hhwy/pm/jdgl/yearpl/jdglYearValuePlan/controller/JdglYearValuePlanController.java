package com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.controller;

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
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.service.IJdglYearValuePlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.domain.JdglYearValuePlan;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglYearValuePlan")
public class JdglYearValuePlanController extends BaseController {

    @Autowired
    private IJdglYearValuePlanService jdglYearValuePlanService;


    //  // @PreAuthorize(hasPermi = "jdglYearValuePlan:list")
    @GetMapping
    public AjaxResult getJdglYearValuePlan(@Validated(ValidationGroups.Get.class) JdglYearValuePlan jdglYearValuePlanParam) {
        JdglYearValuePlan jdglYearValuePlan = jdglYearValuePlanService.getJdglYearValuePlan(jdglYearValuePlanParam);
        return AjaxResult.success(jdglYearValuePlan);
    }

    //  // @PreAuthorize(hasPermi = "jdglYearValuePlan:list")
    @GetMapping("/list")
    public AjaxResult getJdglYearValuePlanList(@Validated(ValidationGroups.Select.class) JdglYearValuePlan jdglYearValuePlanParam) {
        List<JdglYearValuePlan> jdglYearValuePlanList = jdglYearValuePlanService.getJdglYearValuePlanList(jdglYearValuePlanParam);
        return getDataTableAjaxResult(jdglYearValuePlanList);
    }

    // @PreAuthorize(hasPermi = "jdglYearValuePlan:add")
    @PostMapping("/add")
    public AjaxResult insertJdglYearValuePlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglYearValuePlan jdglYearValuePlanParam) {
        jdglYearValuePlanService.insertJdglYearValuePlan(jdglYearValuePlanParam);
        return AjaxResult.success(jdglYearValuePlanParam);
    }

    // @PreAuthorize(hasPermi = "jdglYearValuePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglYearValuePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglYearValuePlan> jdglYearValuePlanListParam) {
        jdglYearValuePlanService.insertJdglYearValuePlanList(jdglYearValuePlanListParam);
        return AjaxResult.success(jdglYearValuePlanListParam);
    }

    // @PreAuthorize(hasPermi = "jdglYearValuePlan:update")
    @PostMapping("/update")
    public AjaxResult updateJdglYearValuePlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglYearValuePlan jdglYearValuePlanParam) {
        return toAjax(jdglYearValuePlanService.updateJdglYearValuePlan(jdglYearValuePlanParam));
    }

    // @PreAuthorize(hasPermi = "jdglYearValuePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglYearValuePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglYearValuePlan> jdglYearValuePlanListParam) {
        return toAjax(jdglYearValuePlanService.updateJdglYearValuePlanList(jdglYearValuePlanListParam));
    }

    // @PreAuthorize(hasPermi = "jdglYearValuePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglYearValuePlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglYearValuePlan jdglYearValuePlanParam) {
        return toAjax(jdglYearValuePlanService.deleteJdglYearValuePlan(jdglYearValuePlanParam));
    }

    // @PreAuthorize(hasPermi = "jdglYearValuePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglYearValuePlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglYearValuePlanPkList = Arrays.asList(ids);
        return toAjax(jdglYearValuePlanService.deleteJdglYearValuePlanByPks(jdglYearValuePlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglYearValuePlan jdglYearValuePlanParam) throws IOException {
        List<JdglYearValuePlan> jdglYearValuePlanList = jdglYearValuePlanService.getJdglYearValuePlanList(jdglYearValuePlanParam);
        ExcelUtils<JdglYearValuePlan> util = new ExcelUtils<>(JdglYearValuePlan.class);
        util.exportExcel(response, jdglYearValuePlanList, DateUtils.getDate());
    }
}
