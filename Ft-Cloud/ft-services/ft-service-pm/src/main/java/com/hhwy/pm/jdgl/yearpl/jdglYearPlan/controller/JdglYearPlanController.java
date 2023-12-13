package com.hhwy.pm.jdgl.yearpl.jdglYearPlan.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.service.IJdglYearPlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:04
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglYearPlan")
public class JdglYearPlanController extends BaseController {

    @Autowired
    private IJdglYearPlanService jdglYearPlanService;


    //  // @PreAuthorize(hasPermi = "jdglYearPlan:list")
    @GetMapping
    public AjaxResult getJdglYearPlan(@Validated(ValidationGroups.Get.class) JdglYearPlan jdglYearPlanParam) {
        JdglYearPlan jdglYearPlan = jdglYearPlanService.getJdglYearPlan(jdglYearPlanParam);
        return AjaxResult.success(jdglYearPlan);
    }

    /**
     * 获取初始化项目合同等数据&未完&
     * @param jdglYearPlanParam
     * @return
     */
    //  // @PreAuthorize(hasPermi = "jdglYearPlan:list")
    @GetMapping("/getInitJdglYearPlan")
    public AjaxResult getInitJdglYearPlan(@Validated(ValidationGroups.Get.class) JdglYearPlan jdglYearPlanParam) {
        return AjaxResult.success(jdglYearPlanService.getInitJdglYearPlan(jdglYearPlanParam));
    }

    //  // @PreAuthorize(hasPermi = "jdglYearPlan:list")
    @GetMapping("/list")
    @CustomLogger(title = "进度管理-计划管理-年度计划", name = "年度计划" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getJdglYearPlanList(@Validated(ValidationGroups.Select.class) JdglYearPlan jdglYearPlanParam) {
        startPage();
        List<JdglYearPlan> jdglYearPlanList = jdglYearPlanService.getJdglYearPlanList(jdglYearPlanParam);
        return getDataTableAjaxResult(jdglYearPlanList);
    }

    // @PreAuthorize(hasPermi = "jdglYearPlan:add")
    @PostMapping("/add")
    @CustomLogger(title = "进度管理-计划管理-年度计划", name = "年度计划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertJdglYearPlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglYearPlan jdglYearPlanParam) {
        jdglYearPlanService.insertJdglYearPlan(jdglYearPlanParam);
        return AjaxResult.success(jdglYearPlanParam);
    }

    // @PreAuthorize(hasPermi = "jdglYearPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglYearPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglYearPlan> jdglYearPlanListParam) {
        jdglYearPlanService.insertJdglYearPlanList(jdglYearPlanListParam);
        return AjaxResult.success(jdglYearPlanListParam);
    }

    // @PreAuthorize(hasPermi = "jdglYearPlan:add")
    @PostMapping("/adjust")
    public AjaxResult adjust(@RequestBody JdglYearPlan jdglYearPlanParam) {
        return AjaxResult.success(jdglYearPlanService.adjust(jdglYearPlanParam));
    }

    // @PreAuthorize(hasPermi = "jdglYearPlan:update")
    @PostMapping("/update")
    @CustomLogger(title = "进度管理-计划管理-年度计划", name = "年度计划" ,businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateJdglYearPlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglYearPlan jdglYearPlanParam) {
        return toAjax(jdglYearPlanService.updateJdglYearPlan(jdglYearPlanParam));
    }

    // @PreAuthorize(hasPermi = "jdglYearPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglYearPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglYearPlan> jdglYearPlanListParam) {
        return toAjax(jdglYearPlanService.updateJdglYearPlanList(jdglYearPlanListParam));
    }

    // @PreAuthorize(hasPermi = "jdglYearPlan:remove")
    @PostMapping("/delete")
    @CustomLogger(title = "进度管理-计划管理-年度计划", name = "年度计划" ,businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteJdglYearPlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglYearPlan jdglYearPlanParam) {
        return toAjax(jdglYearPlanService.deleteJdglYearPlan(jdglYearPlanParam));
    }

    // @PreAuthorize(hasPermi = "jdglYearPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglYearPlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglYearPlanPkList = Arrays.asList(ids);
        return toAjax(jdglYearPlanService.deleteJdglYearPlanByPks(jdglYearPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglYearPlan jdglYearPlanParam) throws IOException {
        List<JdglYearPlan> jdglYearPlanList = jdglYearPlanService.getJdglYearPlanList(jdglYearPlanParam);
        ExcelUtils<JdglYearPlan> util = new ExcelUtils<>(JdglYearPlan.class);
        util.exportExcel(response, jdglYearPlanList, DateUtils.getDate());
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
        jdglYearPlanService.updateTaskStatus(id);
        return AjaxResult.success();
    }
}
