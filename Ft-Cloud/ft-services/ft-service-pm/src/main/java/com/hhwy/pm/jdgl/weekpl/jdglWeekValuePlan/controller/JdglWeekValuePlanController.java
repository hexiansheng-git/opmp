package com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.domain.JdglWeekValuePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.service.IJdglWeekValuePlanService;
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
@RequestMapping("/jdglWeekValuePlan")
public class JdglWeekValuePlanController extends BaseController {

    @Autowired
    private IJdglWeekValuePlanService jdglWeekValuePlanService;


    @PreAuthorize(hasPermi = "jdglWeekValuePlan:list")
    @GetMapping
    public AjaxResult getJdglWeekValuePlan(@Validated(ValidationGroups.Get.class) JdglWeekValuePlan jdglWeekValuePlanParam) {
        JdglWeekValuePlan jdglWeekValuePlan = jdglWeekValuePlanService.getJdglWeekValuePlan(jdglWeekValuePlanParam);
        return AjaxResult.success(jdglWeekValuePlan);
    }

    @PreAuthorize(hasPermi = "jdglWeekValuePlan:list")
    @GetMapping("/list")
    public AjaxResult getJdglWeekValuePlanList(@Validated(ValidationGroups.Select.class) JdglWeekValuePlan jdglWeekValuePlanParam) {
        startPage();
        List<JdglWeekValuePlan> jdglWeekValuePlanList = jdglWeekValuePlanService.getJdglWeekValuePlanList(jdglWeekValuePlanParam);
        return getDataTableAjaxResult(jdglWeekValuePlanList);
    }

    @PreAuthorize(hasPermi = "jdglWeekValuePlan:add")
    @PostMapping("/add")
    public AjaxResult insertJdglWeekValuePlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglWeekValuePlan jdglWeekValuePlanParam) {
        jdglWeekValuePlanService.insertJdglWeekValuePlan(jdglWeekValuePlanParam);
        return AjaxResult.success(jdglWeekValuePlanParam);
    }

    @PreAuthorize(hasPermi = "jdglWeekValuePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglWeekValuePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglWeekValuePlan> jdglWeekValuePlanListParam) {
        jdglWeekValuePlanService.insertJdglWeekValuePlanList(jdglWeekValuePlanListParam);
        return AjaxResult.success(jdglWeekValuePlanListParam);
    }

    @PreAuthorize(hasPermi = "jdglWeekValuePlan:update")
    @PostMapping("/update")
    public AjaxResult updateJdglWeekValuePlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglWeekValuePlan jdglWeekValuePlanParam) {
        return toAjax(jdglWeekValuePlanService.updateJdglWeekValuePlan(jdglWeekValuePlanParam));
    }

    @PreAuthorize(hasPermi = "jdglWeekValuePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglWeekValuePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglWeekValuePlan> jdglWeekValuePlanListParam) {
        return toAjax(jdglWeekValuePlanService.updateJdglWeekValuePlanList(jdglWeekValuePlanListParam));
    }

    @PreAuthorize(hasPermi = "jdglWeekValuePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglWeekValuePlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglWeekValuePlan jdglWeekValuePlanParam) {
        return toAjax(jdglWeekValuePlanService.deleteJdglWeekValuePlan(jdglWeekValuePlanParam));
    }

    @PreAuthorize(hasPermi = "jdglWeekValuePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglWeekValuePlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglWeekValuePlanPkList = Arrays.asList(ids);
        return toAjax(jdglWeekValuePlanService.deleteJdglWeekValuePlanByPks(jdglWeekValuePlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglWeekValuePlan jdglWeekValuePlanParam) throws IOException {
        List<JdglWeekValuePlan> jdglWeekValuePlanList = jdglWeekValuePlanService.getJdglWeekValuePlanList(jdglWeekValuePlanParam);
        ExcelUtils<JdglWeekValuePlan> util = new ExcelUtils<>(JdglWeekValuePlan.class);
        util.exportExcel(response, jdglWeekValuePlanList, DateUtils.getDate());
    }
}
