package com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.domain.JdglMonthValuePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.service.IJdglMonthValuePlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglMonthValuePlan")
public class JdglMonthValuePlanController extends BaseController {

    @Autowired
    private IJdglMonthValuePlanService jdglMonthValuePlanService;


    //  // @PreAuthorize(hasPermi = "jdglMonthValuePlan:list")
    @GetMapping
    public AjaxResult getJdglMonthValuePlan(@Validated(ValidationGroups.Get.class) JdglMonthValuePlan jdglMonthValuePlanParam) {
        JdglMonthValuePlan jdglMonthValuePlan = jdglMonthValuePlanService.getJdglMonthValuePlan(jdglMonthValuePlanParam);
        return AjaxResult.success(jdglMonthValuePlan);
    }

    //  // @PreAuthorize(hasPermi = "jdglMonthValuePlan:list")
    @GetMapping("/list")
    public AjaxResult getJdglMonthValuePlanList(@Validated(ValidationGroups.Select.class) JdglMonthValuePlan jdglMonthValuePlanParam) {
        List<JdglMonthValuePlan> jdglMonthValuePlanList = jdglMonthValuePlanService.getJdglMonthValuePlanList(jdglMonthValuePlanParam);
        return getDataTableAjaxResult(jdglMonthValuePlanList);
    }

    // @PreAuthorize(hasPermi = "jdglMonthValuePlan:add")
    @PostMapping("/add")
    public AjaxResult insertJdglMonthValuePlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglMonthValuePlan jdglMonthValuePlanParam) {
        jdglMonthValuePlanService.insertJdglMonthValuePlan(jdglMonthValuePlanParam);
        return AjaxResult.success(jdglMonthValuePlanParam);
    }

    // @PreAuthorize(hasPermi = "jdglMonthValuePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglMonthValuePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglMonthValuePlan> jdglMonthValuePlanListParam) {
        jdglMonthValuePlanService.insertJdglMonthValuePlanList(jdglMonthValuePlanListParam);
        return AjaxResult.success(jdglMonthValuePlanListParam);
    }

    // @PreAuthorize(hasPermi = "jdglMonthValuePlan:update")
    @PostMapping("/update")
    public AjaxResult updateJdglMonthValuePlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglMonthValuePlan jdglMonthValuePlanParam) {
        return toAjax(jdglMonthValuePlanService.updateJdglMonthValuePlan(jdglMonthValuePlanParam));
    }

    // @PreAuthorize(hasPermi = "jdglMonthValuePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglMonthValuePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglMonthValuePlan> jdglMonthValuePlanListParam) {
        return toAjax(jdglMonthValuePlanService.updateJdglMonthValuePlanList(jdglMonthValuePlanListParam));
    }

    // @PreAuthorize(hasPermi = "jdglMonthValuePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglMonthValuePlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglMonthValuePlan jdglMonthValuePlanParam) {
        return toAjax(jdglMonthValuePlanService.deleteJdglMonthValuePlan(jdglMonthValuePlanParam));
    }

    // @PreAuthorize(hasPermi = "jdglMonthValuePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglMonthValuePlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglMonthValuePlanPkList = Arrays.asList(ids);
        return toAjax(jdglMonthValuePlanService.deleteJdglMonthValuePlanByPks(jdglMonthValuePlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglMonthValuePlan jdglMonthValuePlanParam) throws IOException {
        List<JdglMonthValuePlan> jdglMonthValuePlanList = jdglMonthValuePlanService.getJdglMonthValuePlanList(jdglMonthValuePlanParam);
        ExcelUtils<JdglMonthValuePlan> util = new ExcelUtils<>(JdglMonthValuePlan.class);
        util.exportExcel(response, jdglMonthValuePlanList, DateUtils.getDate());
    }
}
