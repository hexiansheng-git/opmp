package com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.IJdglMonthPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/jdglMonthPlan")
public class JdglMonthPlanController extends BaseController {

    @Autowired
    private IJdglMonthPlanService jdglMonthPlanService;


    //  @PreAuthorize(hasPermi = "jdglMonthPlan:list")
    @GetMapping
    public AjaxResult getJdglMonthPlan(@Validated(ValidationGroups.Get.class) JdglMonthPlan jdglMonthPlanParam) {
        JdglMonthPlan jdglMonthPlan = jdglMonthPlanService.getJdglMonthPlan(jdglMonthPlanParam);
        return AjaxResult.success(jdglMonthPlan);
    }

    /**
     * 获取初始化项目合同等数据&未完&
     * @param jdglMonthPlanParam
     * @return
     */
    //  @PreAuthorize(hasPermi = "jdglMonthPlan:list")
    @GetMapping("/getInitJdglMonthPlan")
    public AjaxResult getInitJdglMonthPlan(@Validated(ValidationGroups.Get.class) JdglMonthPlan jdglMonthPlanParam) {
        return AjaxResult.success(jdglMonthPlanService.getInitJdglMonthPlan(jdglMonthPlanParam));
    }

    //  @PreAuthorize(hasPermi = "jdglMonthPlan:list")
    @GetMapping("/list")
    public AjaxResult getJdglMonthPlanList(@Validated(ValidationGroups.Select.class) JdglMonthPlan jdglMonthPlanParam) {
        startPage();
        List<JdglMonthPlan> jdglMonthPlanList = jdglMonthPlanService.getJdglMonthPlanList(jdglMonthPlanParam);
        return getDataTableAjaxResult(jdglMonthPlanList);
    }

    @PreAuthorize(hasPermi = "jdglMonthPlan:add")
    @PostMapping("/add")
    public AjaxResult insertJdglMonthPlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglMonthPlan jdglMonthPlanParam) {
        jdglMonthPlanService.insertJdglMonthPlan(jdglMonthPlanParam);
        return AjaxResult.success(jdglMonthPlanParam);
    }

    @PreAuthorize(hasPermi = "jdglMonthPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglMonthPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglMonthPlan> jdglMonthPlanListParam) {
        jdglMonthPlanService.insertJdglMonthPlanList(jdglMonthPlanListParam);
        return AjaxResult.success(jdglMonthPlanListParam);
    }

    @PreAuthorize(hasPermi = "jdglMonthPlan:add")
    @PostMapping("/adjust")
    public AjaxResult adjust(@RequestBody JdglMonthPlan jdglMonthPlanParam) {
        return AjaxResult.success(jdglMonthPlanService.adjust(jdglMonthPlanParam));
    }

    @PreAuthorize(hasPermi = "jdglMonthPlan:update")
    @PostMapping("/update")
    public AjaxResult updateJdglMonthPlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglMonthPlan jdglMonthPlanParam) {
        return toAjax(jdglMonthPlanService.updateJdglMonthPlan(jdglMonthPlanParam));
    }

    @PreAuthorize(hasPermi = "jdglMonthPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglMonthPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglMonthPlan> jdglMonthPlanListParam) {
        return toAjax(jdglMonthPlanService.updateJdglMonthPlanList(jdglMonthPlanListParam));
    }

    @PreAuthorize(hasPermi = "jdglMonthPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglMonthPlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglMonthPlan jdglMonthPlanParam) {
        return toAjax(jdglMonthPlanService.deleteJdglMonthPlan(jdglMonthPlanParam));
    }

    @PreAuthorize(hasPermi = "jdglMonthPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglMonthPlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglMonthPlanPkList = Arrays.asList(ids);
        return toAjax(jdglMonthPlanService.deleteJdglMonthPlanByPks(jdglMonthPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglMonthPlan jdglMonthPlanParam) throws IOException {
        List<JdglMonthPlan> jdglMonthPlanList = jdglMonthPlanService.getJdglMonthPlanList(jdglMonthPlanParam);
        ExcelUtils<JdglMonthPlan> util = new ExcelUtils<>(JdglMonthPlan.class);
        util.exportExcel(response, jdglMonthPlanList, DateUtils.getDate());
    }

    /**
     *
     * 更新流程数据
     * @param id 主键
     * @return  监听器
     */
    @RequestMapping(value ="/listener",method = RequestMethod.POST)
    @Transactional
    public AjaxResult updateTaskStatus(@RequestParam ("id") Long id) {
        jdglMonthPlanService.updateTaskStatus(id);
        return AjaxResult.success();
    }
}
