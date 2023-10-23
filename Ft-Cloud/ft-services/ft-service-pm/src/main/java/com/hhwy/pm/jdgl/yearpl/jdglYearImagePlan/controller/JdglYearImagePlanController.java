package com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service.IJdglYearImagePlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglYearImagePlan")
public class JdglYearImagePlanController extends BaseController {

    @Autowired
    private IJdglYearImagePlanService jdglYearImagePlanService;


    //  // @PreAuthorize(hasPermi = "jdglYearImagePlan:list")
    @GetMapping
    public AjaxResult getJdglYearImagePlan(@Validated(ValidationGroups.Get.class) JdglYearImagePlan jdglYearImagePlanParam) {
        JdglYearImagePlan jdglYearImagePlan = jdglYearImagePlanService.getJdglYearImagePlan(jdglYearImagePlanParam);
        return AjaxResult.success(jdglYearImagePlan);
    }

    //  // @PreAuthorize(hasPermi = "jdglYearImagePlan:list")
    @GetMapping("/list")
    public AjaxResult getJdglYearImagePlanList(@Validated(ValidationGroups.Select.class) JdglYearImagePlan jdglYearImagePlanParam) {
        List<JdglYearImagePlan> jdglYearImagePlanList = jdglYearImagePlanService.getJdglYearImagePlanList(jdglYearImagePlanParam);
        return getDataTableAjaxResult(jdglYearImagePlanList);
    }

    // @PreAuthorize(hasPermi = "jdglYearImagePlan:add")
    @PostMapping("/add")
    public AjaxResult insertJdglYearImagePlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglYearImagePlan jdglYearImagePlanParam) {
        jdglYearImagePlanService.insertJdglYearImagePlan(jdglYearImagePlanParam);
        return AjaxResult.success(jdglYearImagePlanParam);
    }

    // @PreAuthorize(hasPermi = "jdglYearImagePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglYearImagePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglYearImagePlan> jdglYearImagePlanListParam) {
        jdglYearImagePlanService.insertJdglYearImagePlanList(jdglYearImagePlanListParam);
        return AjaxResult.success(jdglYearImagePlanListParam);
    }

    // @PreAuthorize(hasPermi = "jdglYearImagePlan:update")
    @PostMapping("/update")
    public AjaxResult updateJdglYearImagePlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglYearImagePlan jdglYearImagePlanParam) {
        return toAjax(jdglYearImagePlanService.updateJdglYearImagePlan(jdglYearImagePlanParam));
    }

    // @PreAuthorize(hasPermi = "jdglYearImagePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglYearImagePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglYearImagePlan> jdglYearImagePlanListParam) {
        return toAjax(jdglYearImagePlanService.updateJdglYearImagePlanList(jdglYearImagePlanListParam));
    }

    // @PreAuthorize(hasPermi = "jdglYearImagePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglYearImagePlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglYearImagePlan jdglYearImagePlanParam) {
        return toAjax(jdglYearImagePlanService.deleteJdglYearImagePlan(jdglYearImagePlanParam));
    }

    // @PreAuthorize(hasPermi = "jdglYearImagePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglYearImagePlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglYearImagePlanPkList = Arrays.asList(ids);
        return toAjax(jdglYearImagePlanService.deleteJdglYearImagePlanByPks(jdglYearImagePlanPkList));
    }

    /**
     * 从总进度计划同步形象计划
     * @param jdglYearPlanParam
     * @return
     */
    @PostMapping("/syncFromTotalPlan")
    public AjaxResult syncFromTotalPlan(@RequestBody JdglYearPlan jdglYearPlanParam) {
        JdglYearPlan jdglYearPlan = jdglYearImagePlanService.syncFromTotalPlan(jdglYearPlanParam);
        return AjaxResult.success(jdglYearPlan);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglYearImagePlan jdglYearImagePlanParam) throws IOException {
        List<JdglYearImagePlan> jdglYearImagePlanList = jdglYearImagePlanService.getJdglYearImagePlanList(jdglYearImagePlanParam);
        ExcelUtils<JdglYearImagePlan> util = new ExcelUtils<>(JdglYearImagePlan.class);
        util.exportExcel(response, jdglYearImagePlanList, DateUtils.getDate());
    }
}
