package com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.domain.JdglWeekImagePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.service.IJdglWeekImagePlanService;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.domain.JdglWeekPlan;
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
@RequestMapping("/jdglWeekImagePlan")
public class JdglWeekImagePlanController extends BaseController {

    @Autowired
    private IJdglWeekImagePlanService jdglWeekImagePlanService;


    @PreAuthorize(hasPermi = "jdglWeekImagePlan:list")
    @GetMapping
    public AjaxResult getJdglWeekImagePlan(@Validated(ValidationGroups.Get.class) JdglWeekImagePlan jdglWeekImagePlanParam) {
        JdglWeekImagePlan jdglWeekImagePlan = jdglWeekImagePlanService.getJdglWeekImagePlan(jdglWeekImagePlanParam);
        return AjaxResult.success(jdglWeekImagePlan);
    }

    @PreAuthorize(hasPermi = "jdglWeekImagePlan:list")
    @GetMapping("/list")
    public AjaxResult getJdglWeekImagePlanList(@Validated(ValidationGroups.Select.class) JdglWeekImagePlan jdglWeekImagePlanParam) {
        List<JdglWeekImagePlan> jdglWeekImagePlanList = jdglWeekImagePlanService.getJdglWeekImagePlanList(jdglWeekImagePlanParam);
        return getDataTableAjaxResult(jdglWeekImagePlanList);
    }

    @PreAuthorize(hasPermi = "jdglWeekImagePlan:add")
    @PostMapping("/add")
    public AjaxResult insertJdglWeekImagePlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglWeekImagePlan jdglWeekImagePlanParam) {
        jdglWeekImagePlanService.insertJdglWeekImagePlan(jdglWeekImagePlanParam);
        return AjaxResult.success(jdglWeekImagePlanParam);
    }

    @PreAuthorize(hasPermi = "jdglWeekImagePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglWeekImagePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglWeekImagePlan> jdglWeekImagePlanListParam) {
        jdglWeekImagePlanService.insertJdglWeekImagePlanList(jdglWeekImagePlanListParam);
        return AjaxResult.success(jdglWeekImagePlanListParam);
    }

    @PreAuthorize(hasPermi = "jdglWeekImagePlan:update")
    @PostMapping("/update")
    public AjaxResult updateJdglWeekImagePlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglWeekImagePlan jdglWeekImagePlanParam) {
        return toAjax(jdglWeekImagePlanService.updateJdglWeekImagePlan(jdglWeekImagePlanParam));
    }

    @PreAuthorize(hasPermi = "jdglWeekImagePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglWeekImagePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglWeekImagePlan> jdglWeekImagePlanListParam) {
        return toAjax(jdglWeekImagePlanService.updateJdglWeekImagePlanList(jdglWeekImagePlanListParam));
    }

    @PreAuthorize(hasPermi = "jdglWeekImagePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglWeekImagePlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglWeekImagePlan jdglWeekImagePlanParam) {
        return toAjax(jdglWeekImagePlanService.deleteJdglWeekImagePlan(jdglWeekImagePlanParam));
    }

    @PreAuthorize(hasPermi = "jdglWeekImagePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglWeekImagePlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglWeekImagePlanPkList = Arrays.asList(ids);
        return toAjax(jdglWeekImagePlanService.deleteJdglWeekImagePlanByPks(jdglWeekImagePlanPkList));
    }

    /**
     * 从总进度计划同步形象计划
     * @param jdglWeekPlanParam
     * @return
     */
    @PostMapping("/syncFromTotalPlan")
    public AjaxResult syncFromTotalPlan(@RequestBody JdglWeekPlan jdglWeekPlanParam) {
        return AjaxResult.success(jdglWeekImagePlanService.syncFromTotalPlan(jdglWeekPlanParam));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglWeekImagePlan jdglWeekImagePlanParam) throws IOException {
        List<JdglWeekImagePlan> jdglWeekImagePlanList = jdglWeekImagePlanService.getJdglWeekImagePlanList(jdglWeekImagePlanParam);
        ExcelUtils<JdglWeekImagePlan> util = new ExcelUtils<>(JdglWeekImagePlan.class);
        util.exportExcel(response, jdglWeekImagePlanList, DateUtils.getDate());
    }
}
