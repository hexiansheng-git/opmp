package com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.domain.JdglQuarterImagePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.service.IJdglQuarterImagePlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;
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
@RequestMapping("/jdglQuarterImagePlan")
public class JdglQuarterImagePlanController extends BaseController {

    @Autowired
    private IJdglQuarterImagePlanService jdglQuarterImagePlanService;


    @PreAuthorize(hasPermi = "jdglQuarterImagePlan:list")
    @GetMapping
    public AjaxResult getJdglQuarterImagePlan(@Validated(ValidationGroups.Get.class) JdglQuarterImagePlan jdglQuarterImagePlanParam) {
        JdglQuarterImagePlan jdglQuarterImagePlan = jdglQuarterImagePlanService.getJdglQuarterImagePlan(jdglQuarterImagePlanParam);
        return AjaxResult.success(jdglQuarterImagePlan);
    }

    @PreAuthorize(hasPermi = "jdglQuarterImagePlan:list")
    @GetMapping("/list")
    public AjaxResult getJdglQuarterImagePlanList(@Validated(ValidationGroups.Select.class) JdglQuarterImagePlan jdglQuarterImagePlanParam) {
        startPage();
        List<JdglQuarterImagePlan> jdglQuarterImagePlanList = jdglQuarterImagePlanService.getJdglQuarterImagePlanList(jdglQuarterImagePlanParam);
        return getDataTableAjaxResult(jdglQuarterImagePlanList);
    }

    @PreAuthorize(hasPermi = "jdglQuarterImagePlan:add")
    @PostMapping("/add")
    public AjaxResult insertJdglQuarterImagePlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglQuarterImagePlan jdglQuarterImagePlanParam) {
        jdglQuarterImagePlanService.insertJdglQuarterImagePlan(jdglQuarterImagePlanParam);
        return AjaxResult.success(jdglQuarterImagePlanParam);
    }

    @PreAuthorize(hasPermi = "jdglQuarterImagePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglQuarterImagePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglQuarterImagePlan> jdglQuarterImagePlanListParam) {
        jdglQuarterImagePlanService.insertJdglQuarterImagePlanList(jdglQuarterImagePlanListParam);
        return AjaxResult.success(jdglQuarterImagePlanListParam);
    }

    @PreAuthorize(hasPermi = "jdglQuarterImagePlan:update")
    @PostMapping("/update")
    public AjaxResult updateJdglQuarterImagePlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglQuarterImagePlan jdglQuarterImagePlanParam) {
        return toAjax(jdglQuarterImagePlanService.updateJdglQuarterImagePlan(jdglQuarterImagePlanParam));
    }

    @PreAuthorize(hasPermi = "jdglQuarterImagePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglQuarterImagePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglQuarterImagePlan> jdglQuarterImagePlanListParam) {
        return toAjax(jdglQuarterImagePlanService.updateJdglQuarterImagePlanList(jdglQuarterImagePlanListParam));
    }

    @PreAuthorize(hasPermi = "jdglQuarterImagePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglQuarterImagePlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglQuarterImagePlan jdglQuarterImagePlanParam) {
        return toAjax(jdglQuarterImagePlanService.deleteJdglQuarterImagePlan(jdglQuarterImagePlanParam));
    }

    @PreAuthorize(hasPermi = "jdglQuarterImagePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglQuarterImagePlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglQuarterImagePlanPkList = Arrays.asList(ids);
        return toAjax(jdglQuarterImagePlanService.deleteJdglQuarterImagePlanByPks(jdglQuarterImagePlanPkList));
    }

    /**
     * 从总进度计划同步形象计划
     * @param jdglQuarterPlanParam
     * @return
     */
    @PostMapping("/syncFromTotalPlan")
    public AjaxResult syncFromTotalPlan(JdglQuarterPlan jdglQuarterPlanParam) {
        return AjaxResult.success(jdglQuarterImagePlanService.syncFromTotalPlan(jdglQuarterPlanParam));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglQuarterImagePlan jdglQuarterImagePlanParam) throws IOException {
        List<JdglQuarterImagePlan> jdglQuarterImagePlanList = jdglQuarterImagePlanService.getJdglQuarterImagePlanList(jdglQuarterImagePlanParam);
        ExcelUtils<JdglQuarterImagePlan> util = new ExcelUtils<>(JdglQuarterImagePlan.class);
        util.exportExcel(response, jdglQuarterImagePlanList, DateUtils.getDate());
    }
}
