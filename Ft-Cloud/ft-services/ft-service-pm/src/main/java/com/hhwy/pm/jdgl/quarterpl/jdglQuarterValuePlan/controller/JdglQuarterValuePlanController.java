package com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.domain.JdglQuarterValuePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.service.IJdglQuarterValuePlanService;
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
@RequestMapping("/jdglQuarterValuePlan")
public class JdglQuarterValuePlanController extends BaseController {

    @Autowired
    private IJdglQuarterValuePlanService jdglQuarterValuePlanService;


    @PreAuthorize(hasPermi = "jdglQuarterValuePlan:list")
    @GetMapping
    public AjaxResult getJdglQuarterValuePlan(@Validated(ValidationGroups.Get.class) JdglQuarterValuePlan jdglQuarterValuePlanParam) {
        JdglQuarterValuePlan jdglQuarterValuePlan = jdglQuarterValuePlanService.getJdglQuarterValuePlan(jdglQuarterValuePlanParam);
        return AjaxResult.success(jdglQuarterValuePlan);
    }

    @PreAuthorize(hasPermi = "jdglQuarterValuePlan:list")
    @GetMapping("/list")
    public AjaxResult getJdglQuarterValuePlanList(@Validated(ValidationGroups.Select.class) JdglQuarterValuePlan jdglQuarterValuePlanParam) {
        startPage();
        List<JdglQuarterValuePlan> jdglQuarterValuePlanList = jdglQuarterValuePlanService.getJdglQuarterValuePlanList(jdglQuarterValuePlanParam);
        return getDataTableAjaxResult(jdglQuarterValuePlanList);
    }

    @PreAuthorize(hasPermi = "jdglQuarterValuePlan:add")
    @PostMapping("/add")
    public AjaxResult insertJdglQuarterValuePlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglQuarterValuePlan jdglQuarterValuePlanParam) {
        jdglQuarterValuePlanService.insertJdglQuarterValuePlan(jdglQuarterValuePlanParam);
        return AjaxResult.success(jdglQuarterValuePlanParam);
    }

    @PreAuthorize(hasPermi = "jdglQuarterValuePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglQuarterValuePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglQuarterValuePlan> jdglQuarterValuePlanListParam) {
        jdglQuarterValuePlanService.insertJdglQuarterValuePlanList(jdglQuarterValuePlanListParam);
        return AjaxResult.success(jdglQuarterValuePlanListParam);
    }

    @PreAuthorize(hasPermi = "jdglQuarterValuePlan:update")
    @PostMapping("/update")
    public AjaxResult updateJdglQuarterValuePlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglQuarterValuePlan jdglQuarterValuePlanParam) {
        return toAjax(jdglQuarterValuePlanService.updateJdglQuarterValuePlan(jdglQuarterValuePlanParam));
    }

    @PreAuthorize(hasPermi = "jdglQuarterValuePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglQuarterValuePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglQuarterValuePlan> jdglQuarterValuePlanListParam) {
        return toAjax(jdglQuarterValuePlanService.updateJdglQuarterValuePlanList(jdglQuarterValuePlanListParam));
    }

    @PreAuthorize(hasPermi = "jdglQuarterValuePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglQuarterValuePlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglQuarterValuePlan jdglQuarterValuePlanParam) {
        return toAjax(jdglQuarterValuePlanService.deleteJdglQuarterValuePlan(jdglQuarterValuePlanParam));
    }

    @PreAuthorize(hasPermi = "jdglQuarterValuePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglQuarterValuePlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglQuarterValuePlanPkList = Arrays.asList(ids);
        return toAjax(jdglQuarterValuePlanService.deleteJdglQuarterValuePlanByPks(jdglQuarterValuePlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglQuarterValuePlan jdglQuarterValuePlanParam) throws IOException {
        List<JdglQuarterValuePlan> jdglQuarterValuePlanList = jdglQuarterValuePlanService.getJdglQuarterValuePlanList(jdglQuarterValuePlanParam);
        ExcelUtils<JdglQuarterValuePlan> util = new ExcelUtils<>(JdglQuarterValuePlan.class);
        util.exportExcel(response, jdglQuarterValuePlanList, DateUtils.getDate());
    }
}
