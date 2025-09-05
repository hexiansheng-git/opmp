package com.hhwy.pm.jdgl.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.domain.JdglDatePlan;
import com.hhwy.pm.jdgl.service.IJdglDatePlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author fushudong
 * @date 2023-08-14 17:55:25
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglDatePlan")
public class JdglDatePlanController extends BaseController {
    @Autowired
    private IJdglDatePlanService jdglDatePlanService;

    @PreAuthorize(hasPermi = "jdglDatePlan:list")
    @GetMapping
    public AjaxResult getJdglDatePlan(@Validated(ValidationGroups.Get.class) JdglDatePlan jdglDatePlanParam) {
        JdglDatePlan jdglDatePlan = jdglDatePlanService.getJdglDatePlan(jdglDatePlanParam);
        return AjaxResult.success(jdglDatePlan);
    }

    //    @PreAuthorize(hasPermi = "jdglDatePlan:list")
    @GetMapping("/list")
    public AjaxResult getJdglDatePlanList(@Validated(ValidationGroups.Select.class) JdglDatePlan jdglDatePlanParam) {
        startPage();
        List<JdglDatePlan> jdglDatePlanList = jdglDatePlanService.getJdglDatePlanList(jdglDatePlanParam);
        return getDataTableAjaxResult(jdglDatePlanList);
    }

    @PreAuthorize(hasPermi = "jdglDatePlan:add")
    @PostMapping("/add")
    public AjaxResult insertJdglDatePlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglDatePlan jdglDatePlanParam) {
        jdglDatePlanService.insertJdglDatePlan(jdglDatePlanParam);
        return AjaxResult.success(jdglDatePlanParam);
    }

    @PreAuthorize(hasPermi = "jdglDatePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglDatePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglDatePlan> jdglDatePlanListParam) {
        jdglDatePlanService.insertJdglDatePlanList(jdglDatePlanListParam);
        return AjaxResult.success(jdglDatePlanListParam);
    }

    @PreAuthorize(hasPermi = "jdglDatePlan:update")
    @PostMapping("/update")
    public AjaxResult updateJdglDatePlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglDatePlan jdglDatePlanParam) {
        return toAjax(jdglDatePlanService.updateJdglDatePlan(jdglDatePlanParam));
    }

    @PreAuthorize(hasPermi = "jdglDatePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglDatePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglDatePlan> jdglDatePlanListParam) {
        return toAjax(jdglDatePlanService.updateJdglDatePlanList(jdglDatePlanListParam));
    }

    @PreAuthorize(hasPermi = "jdglDatePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglDatePlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglDatePlan jdglDatePlanParam) {
        return toAjax(jdglDatePlanService.deleteJdglDatePlan(jdglDatePlanParam));
    }

    @PreAuthorize(hasPermi = "jdglDatePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglDatePlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglDatePlanPkList = Arrays.asList(ids);
        return toAjax(jdglDatePlanService.deleteJdglDatePlanByPks(jdglDatePlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglDatePlan jdglDatePlanParam) throws IOException {
        List<JdglDatePlan> jdglDatePlanList = jdglDatePlanService.getJdglDatePlanList(jdglDatePlanParam);
        ExcelUtils<JdglDatePlan> util = new ExcelUtils<>(JdglDatePlan.class);
        util.exportExcel(response, jdglDatePlanList, DateUtils.getDate());
    }
}
