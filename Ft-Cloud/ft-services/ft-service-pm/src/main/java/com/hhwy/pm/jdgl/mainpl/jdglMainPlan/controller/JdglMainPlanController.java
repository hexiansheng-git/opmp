package com.hhwy.pm.jdgl.mainpl.jdglMainPlan.controller;

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
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:20
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglMainPlan")
public class JdglMainPlanController extends BaseController {

    @Autowired
    private IJdglMainPlanService jdglMainPlanService;


    @PreAuthorize(hasPermi = "jdglMainPlan:list")
    @GetMapping
    public AjaxResult getJdglMainPlan(@Validated(ValidationGroups.Get.class) JdglMainPlan jdglMainPlanParam) {
        JdglMainPlan jdglMainPlan = jdglMainPlanService.getJdglMainPlan(jdglMainPlanParam);
        return AjaxResult.success(jdglMainPlan);
    }

    @PreAuthorize(hasPermi = "jdglMainPlan:list")
    @GetMapping("/getUsingMainPlan")
    public AjaxResult getUsingMainPlan() {
        JdglMainPlan jdglMainPlan = jdglMainPlanService.getUsingJdglMainPlan();
        return AjaxResult.success(jdglMainPlan);
    }

    @PreAuthorize(hasPermi = "jdglMainPlan:list")
    @GetMapping("/list")
    public AjaxResult getJdglMainPlanList(@Validated(ValidationGroups.Select.class) JdglMainPlan jdglMainPlanParam) {
        startPage();
        List<JdglMainPlan> jdglMainPlanList = jdglMainPlanService.getJdglMainPlanList(jdglMainPlanParam);
        return getDataTableAjaxResult(jdglMainPlanList);
    }

    @PreAuthorize(hasPermi = "jdglMainPlan:add")
    @PostMapping("/add")
    public AjaxResult insertJdglMainPlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglMainPlan jdglMainPlanParam) {
        jdglMainPlanService.insertJdglMainPlan(jdglMainPlanParam);
        return AjaxResult.success(jdglMainPlanParam);
    }

    @PreAuthorize(hasPermi = "jdglMainPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglMainPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglMainPlan> jdglMainPlanListParam) {
        jdglMainPlanService.insertJdglMainPlanList(jdglMainPlanListParam);
        return AjaxResult.success(jdglMainPlanListParam);
    }

    @PreAuthorize(hasPermi = "jdglMainPlan:update")
    @PostMapping("/update")
    public AjaxResult updateJdglMainPlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglMainPlan jdglMainPlanParam) {
        return toAjax(jdglMainPlanService.updateJdglMainPlan(jdglMainPlanParam));
    }

    @PreAuthorize(hasPermi = "jdglMainPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglMainPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglMainPlan> jdglMainPlanListParam) {
        return toAjax(jdglMainPlanService.updateJdglMainPlanList(jdglMainPlanListParam));
    }

    @PreAuthorize(hasPermi = "jdglMainPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglMainPlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglMainPlan jdglMainPlanParam) {
        return toAjax(jdglMainPlanService.deleteJdglMainPlan(jdglMainPlanParam));
    }

    @PreAuthorize(hasPermi = "jdglMainPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglMainPlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglMainPlanPkList = Arrays.asList(ids);
        return toAjax(jdglMainPlanService.deleteJdglMainPlanByPks(jdglMainPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglMainPlan jdglMainPlanParam) throws IOException {
        List<JdglMainPlan> jdglMainPlanList = jdglMainPlanService.getJdglMainPlanList(jdglMainPlanParam);
        ExcelUtils<JdglMainPlan> util = new ExcelUtils<>(JdglMainPlan.class);
        util.exportExcel(response, jdglMainPlanList, DateUtils.getDate());
    }
}
