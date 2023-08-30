package com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service.IJdglMonthImagePlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
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
 * @date 2023-08-21 15:48:25
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglMonthImagePlan")
public class JdglMonthImagePlanController extends BaseController {

    @Autowired
    private IJdglMonthImagePlanService jdglMonthImagePlanService;


    @PreAuthorize(hasPermi = "jdglMonthImagePlan:list")
    @GetMapping
    public AjaxResult getJdglMonthImagePlan(@Validated(ValidationGroups.Get.class) JdglMonthImagePlan jdglMonthImagePlanParam) {
        JdglMonthImagePlan jdglMonthImagePlan = jdglMonthImagePlanService.getJdglMonthImagePlan(jdglMonthImagePlanParam);
        return AjaxResult.success(jdglMonthImagePlan);
    }

    @PreAuthorize(hasPermi = "jdglMonthImagePlan:list")
    @GetMapping("/list")
    public AjaxResult getJdglMonthImagePlanList(@Validated(ValidationGroups.Select.class) JdglMonthImagePlan jdglMonthImagePlanParam) {
        List<JdglMonthImagePlan> jdglMonthImagePlanList = jdglMonthImagePlanService.getJdglMonthImagePlanList(jdglMonthImagePlanParam);
        return getDataTableAjaxResult(jdglMonthImagePlanList);
    }

    @PreAuthorize(hasPermi = "jdglMonthImagePlan:add")
    @PostMapping("/add")
    public AjaxResult insertJdglMonthImagePlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglMonthImagePlan jdglMonthImagePlanParam) {
        jdglMonthImagePlanService.insertJdglMonthImagePlan(jdglMonthImagePlanParam);
        return AjaxResult.success(jdglMonthImagePlanParam);
    }

    @PreAuthorize(hasPermi = "jdglMonthImagePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglMonthImagePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglMonthImagePlan> jdglMonthImagePlanListParam) {
        jdglMonthImagePlanService.insertJdglMonthImagePlanList(jdglMonthImagePlanListParam);
        return AjaxResult.success(jdglMonthImagePlanListParam);
    }

    @PreAuthorize(hasPermi = "jdglMonthImagePlan:update")
    @PostMapping("/update")
    public AjaxResult updateJdglMonthImagePlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglMonthImagePlan jdglMonthImagePlanParam) {
        return toAjax(jdglMonthImagePlanService.updateJdglMonthImagePlan(jdglMonthImagePlanParam));
    }

    @PreAuthorize(hasPermi = "jdglMonthImagePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglMonthImagePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglMonthImagePlan> jdglMonthImagePlanListParam) {
        return toAjax(jdglMonthImagePlanService.updateJdglMonthImagePlanList(jdglMonthImagePlanListParam));
    }

    @PreAuthorize(hasPermi = "jdglMonthImagePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglMonthImagePlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglMonthImagePlan jdglMonthImagePlanParam) {
        return toAjax(jdglMonthImagePlanService.deleteJdglMonthImagePlan(jdglMonthImagePlanParam));
    }

    @PreAuthorize(hasPermi = "jdglMonthImagePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglMonthImagePlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglMonthImagePlanPkList = Arrays.asList(ids);
        return toAjax(jdglMonthImagePlanService.deleteJdglMonthImagePlanByPks(jdglMonthImagePlanPkList));
    }

    /**
     * 从总进度计划同步形象计划
     * @param jdglMonthPlanParam
     * @return
     */
    @PostMapping("/syncFromTotalPlan")
    public AjaxResult syncFromTotalPlan(JdglMonthPlan jdglMonthPlanParam) {
        return AjaxResult.success(jdglMonthImagePlanService.syncFromTotalPlan(jdglMonthPlanParam));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglMonthImagePlan jdglMonthImagePlanParam) throws IOException {
        List<JdglMonthImagePlan> jdglMonthImagePlanList = jdglMonthImagePlanService.getJdglMonthImagePlanList(jdglMonthImagePlanParam);
        ExcelUtils<JdglMonthImagePlan> util = new ExcelUtils<>(JdglMonthImagePlan.class);
        util.exportExcel(response, jdglMonthImagePlanList, DateUtils.getDate());
    }
}
