package com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.service.IJdglQuarterPlanService;
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
@RequestMapping("/jdglQuarterPlan")
public class JdglQuarterPlanController extends BaseController {

    @Autowired
    private IJdglQuarterPlanService jdglQuarterPlanService;


    //  @PreAuthorize(hasPermi = "jdglQuarterPlan:list")
    @GetMapping
    public AjaxResult getJdglQuarterPlan(@Validated(ValidationGroups.Get.class) JdglQuarterPlan jdglQuarterPlanParam) {
        JdglQuarterPlan jdglQuarterPlan = jdglQuarterPlanService.getJdglQuarterPlan(jdglQuarterPlanParam);
        return AjaxResult.success(jdglQuarterPlan);
    }

    /**
     * 获取初始化项目合同等数据&未完&
     * @param jdglQuarterPlanParam
     * @return
     */
    //  @PreAuthorize(hasPermi = "jdglQuarterPlan:list")
    @GetMapping("/getInitJdglQuarterPlan")
    public AjaxResult getInitJdglQuarterPlan(@Validated(ValidationGroups.Get.class) JdglQuarterPlan jdglQuarterPlanParam) {
        return AjaxResult.success(jdglQuarterPlanService.getInitJdglQuarterPlan(jdglQuarterPlanParam));
    }

    //  @PreAuthorize(hasPermi = "jdglQuarterPlan:list")
    @GetMapping("/list")
    public AjaxResult getJdglQuarterPlanList(@Validated(ValidationGroups.Select.class) JdglQuarterPlan jdglQuarterPlanParam) {
        startPage();
        List<JdglQuarterPlan> jdglQuarterPlanList = jdglQuarterPlanService.getJdglQuarterPlanList(jdglQuarterPlanParam);
        return getDataTableAjaxResult(jdglQuarterPlanList);
    }

    @PreAuthorize(hasPermi = "jdglQuarterPlan:add")
    @PostMapping("/add")
    public AjaxResult insertJdglQuarterPlan(@Validated(ValidationGroups.Save.class) @RequestBody JdglQuarterPlan jdglQuarterPlanParam) {
        jdglQuarterPlanService.insertJdglQuarterPlan(jdglQuarterPlanParam);
        return AjaxResult.success(jdglQuarterPlanParam);
    }

    @PreAuthorize(hasPermi = "jdglQuarterPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglQuarterPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglQuarterPlan> jdglQuarterPlanListParam) {
        jdglQuarterPlanService.insertJdglQuarterPlanList(jdglQuarterPlanListParam);
        return AjaxResult.success(jdglQuarterPlanListParam);
    }

    @PreAuthorize(hasPermi = "jdglQuarterPlan:add")
    @PostMapping("/adjust")
    public AjaxResult adjust(@RequestBody JdglQuarterPlan jdglQuarterPlanParam) {
        return AjaxResult.success(jdglQuarterPlanService.adjust(jdglQuarterPlanParam));
    }

    @PreAuthorize(hasPermi = "jdglQuarterPlan:update")
    @PostMapping("/update")
    public AjaxResult updateJdglQuarterPlan(@Validated(ValidationGroups.Update.class) @RequestBody JdglQuarterPlan jdglQuarterPlanParam) {
        return toAjax(jdglQuarterPlanService.updateJdglQuarterPlan(jdglQuarterPlanParam));
    }

    @PreAuthorize(hasPermi = "jdglQuarterPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglQuarterPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglQuarterPlan> jdglQuarterPlanListParam) {
        return toAjax(jdglQuarterPlanService.updateJdglQuarterPlanList(jdglQuarterPlanListParam));
    }

    @PreAuthorize(hasPermi = "jdglQuarterPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglQuarterPlan(@Validated(ValidationGroups.Delete.class) @RequestBody JdglQuarterPlan jdglQuarterPlanParam) {
        return toAjax(jdglQuarterPlanService.deleteJdglQuarterPlan(jdglQuarterPlanParam));
    }

    @PreAuthorize(hasPermi = "jdglQuarterPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglQuarterPlanByPks(@PathVariable Long[] ids) {
        List<Long> jdglQuarterPlanPkList = Arrays.asList(ids);
        return toAjax(jdglQuarterPlanService.deleteJdglQuarterPlanByPks(jdglQuarterPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglQuarterPlan jdglQuarterPlanParam) throws IOException {
        List<JdglQuarterPlan> jdglQuarterPlanList = jdglQuarterPlanService.getJdglQuarterPlanList(jdglQuarterPlanParam);
        ExcelUtils<JdglQuarterPlan> util = new ExcelUtils<>(JdglQuarterPlan.class);
        util.exportExcel(response, jdglQuarterPlanList, DateUtils.getDate());
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
        jdglQuarterPlanService.updateTaskStatus(id);
        return AjaxResult.success();
    }

}
