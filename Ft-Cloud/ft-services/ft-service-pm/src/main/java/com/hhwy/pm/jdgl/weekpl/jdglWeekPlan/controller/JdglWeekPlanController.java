package com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.domain.JdglWeekPlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.service.IJdglWeekPlanService;
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
 * @date 2023-08-21 15:48:04
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglWeekPlan")
public class JdglWeekPlanController extends BaseController {

    @Autowired
    private IJdglWeekPlanService jdglWeekPlanService;


    @PreAuthorize(hasPermi = "jdglWeekPlan:list")
    @GetMapping
    public AjaxResult getJdglWeekPlan(@Validated(ValidationGroups.Get.class) JdglWeekPlan jdglWeekPlanParam) {
        JdglWeekPlan jdglWeekPlan = jdglWeekPlanService.getJdglWeekPlan(jdglWeekPlanParam);
        return AjaxResult.success(jdglWeekPlan);
    }

    /**
     * 获取初始化项目合同等数据&未完&
     * @param jdglWeekPlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "jdglWeekPlan:list")
    @GetMapping("/getInitJdglWeekPlan")
    public AjaxResult getInitJdglWeekPlan(@Validated(ValidationGroups.Get.class) JdglWeekPlan jdglWeekPlanParam) {
        return AjaxResult.success(jdglWeekPlanService.getInitJdglWeekPlan(jdglWeekPlanParam));
    }

    @PreAuthorize(hasPermi = "jdglWeekPlan:list")
    @GetMapping("/list")
    public AjaxResult getJdglWeekPlanList(@Validated(ValidationGroups.Select.class) JdglWeekPlan jdglWeekPlanParam) {
        startPage();
        List<JdglWeekPlan> jdglWeekPlanList = jdglWeekPlanService.getJdglWeekPlanList(jdglWeekPlanParam);
        return getDataTableAjaxResult(jdglWeekPlanList);
    }

    @PreAuthorize(hasPermi = "jdglWeekPlan:add")
    @PostMapping("/add")
    public AjaxResult insertJdglWeekPlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglWeekPlan jdglWeekPlanParam) {
        jdglWeekPlanService.insertJdglWeekPlan(jdglWeekPlanParam);
        return AjaxResult.success(jdglWeekPlanParam);
    }

    @PreAuthorize(hasPermi = "jdglWeekPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglWeekPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglWeekPlan> jdglWeekPlanListParam) {
        jdglWeekPlanService.insertJdglWeekPlanList(jdglWeekPlanListParam);
        return AjaxResult.success(jdglWeekPlanListParam);
    }

    @PreAuthorize(hasPermi = "jdglWeekPlan:add")
    @PostMapping("/adjust")
    public AjaxResult adjust(@RequestBody JdglWeekPlan jdglWeekPlanParam) {
        jdglWeekPlanService.adjust(jdglWeekPlanParam);
        return AjaxResult.success(jdglWeekPlanParam);
    }

    @PreAuthorize(hasPermi = "jdglWeekPlan:update")
    @PostMapping("/update")
    public AjaxResult updateJdglWeekPlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglWeekPlan jdglWeekPlanParam) {
        return toAjax(jdglWeekPlanService.updateJdglWeekPlan(jdglWeekPlanParam));
    }

    @PreAuthorize(hasPermi = "jdglWeekPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglWeekPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglWeekPlan> jdglWeekPlanListParam) {
        return toAjax(jdglWeekPlanService.updateJdglWeekPlanList(jdglWeekPlanListParam));
    }

    @PreAuthorize(hasPermi = "jdglWeekPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglWeekPlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglWeekPlan jdglWeekPlanParam) {
        return toAjax(jdglWeekPlanService.deleteJdglWeekPlan(jdglWeekPlanParam));
    }

    @PreAuthorize(hasPermi = "jdglWeekPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglWeekPlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglWeekPlanPkList = Arrays.asList(ids);
        return toAjax(jdglWeekPlanService.deleteJdglWeekPlanByPks(jdglWeekPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglWeekPlan jdglWeekPlanParam) throws IOException {
        List<JdglWeekPlan> jdglWeekPlanList = jdglWeekPlanService.getJdglWeekPlanList(jdglWeekPlanParam);
        ExcelUtils<JdglWeekPlan> util = new ExcelUtils<>(JdglWeekPlan.class);
        util.exportExcel(response, jdglWeekPlanList, DateUtils.getDate());
    }
}
