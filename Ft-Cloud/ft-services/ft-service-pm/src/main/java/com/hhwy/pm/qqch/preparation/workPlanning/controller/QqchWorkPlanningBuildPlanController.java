package com.hhwy.pm.qqch.preparation.workPlanning.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import cn.hutool.poi.excel.ExcelUtil;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningBuildPlan;
import com.hhwy.pm.qqch.preparation.workPlanning.service.IQqchWorkPlanningBuildPlanService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zq
 * @date 2023-07-19 11:30:27
 * @remark 前期策划编制-施工策划-1.4.2营地场站规划
 */
@Validated
@RestController
@RequestMapping("/qqchWorkPlanningBuildPlan")
public class QqchWorkPlanningBuildPlanController extends BaseController {

    @Autowired
    private IQqchWorkPlanningBuildPlanService qqchWorkPlanningBuildPlanService;


    @PreAuthorize(hasPermi = "qqchWorkPlanningBuildPlan:list")
    @GetMapping
    public AjaxResult getQqchWorkPlanningBuildPlan(@Validated(ValidationGroups.Get.class) QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlanParam) {
        QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan = qqchWorkPlanningBuildPlanService.getQqchWorkPlanningBuildPlan(qqchWorkPlanningBuildPlanParam);
        return AjaxResult.success(qqchWorkPlanningBuildPlan);
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanningBuildPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchWorkPlanningBuildPlanList(@Validated(ValidationGroups.Select.class) QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlanParam) {
        startPage();
        List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlanList = qqchWorkPlanningBuildPlanService.getQqchWorkPlanningBuildPlanList(qqchWorkPlanningBuildPlanParam);
        return getDataTableAjaxResult(qqchWorkPlanningBuildPlanList);
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanningBuildPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchWorkPlanningBuildPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlanParam) {
        qqchWorkPlanningBuildPlanService.insertQqchWorkPlanningBuildPlan(qqchWorkPlanningBuildPlanParam);
        return AjaxResult.success(qqchWorkPlanningBuildPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanningBuildPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchWorkPlanningBuildPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlanListParam) {
        try{
            qqchWorkPlanningBuildPlanService.insertQqchWorkPlanningBuildPlanList(qqchWorkPlanningBuildPlanListParam);
            return AjaxResult.success(qqchWorkPlanningBuildPlanListParam);
        }catch (Exception e){
         e.printStackTrace();
         return AjaxResult.error(e.getMessage());
        }
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanningBuildPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchWorkPlanningBuildPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlanParam) {
        return toAjax(qqchWorkPlanningBuildPlanService.updateQqchWorkPlanningBuildPlan(qqchWorkPlanningBuildPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanningBuildPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchWorkPlanningBuildPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlanListParam) {
        return toAjax(qqchWorkPlanningBuildPlanService.updateQqchWorkPlanningBuildPlanList(qqchWorkPlanningBuildPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanningBuildPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchWorkPlanningBuildPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlanParam) {
        return toAjax(qqchWorkPlanningBuildPlanService.deleteQqchWorkPlanningBuildPlan(qqchWorkPlanningBuildPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanningBuildPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchWorkPlanningBuildPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchWorkPlanningBuildPlanPkList = Arrays.asList(ids);
        return toAjax(qqchWorkPlanningBuildPlanService.deleteQqchWorkPlanningBuildPlanByPks(qqchWorkPlanningBuildPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlanParam) throws IOException {
        List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlanList = qqchWorkPlanningBuildPlanService.getQqchWorkPlanningBuildPlanList(qqchWorkPlanningBuildPlanParam);
        ExcelUtils<QqchWorkPlanningBuildPlan> util = new ExcelUtils<>(QqchWorkPlanningBuildPlan.class);
        util.exportExcel(response, qqchWorkPlanningBuildPlanList, DateUtils.getDate());
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PreAuthorize(hasPermi = "qqchWorkPlanningBuildPlan:importData")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file){
        try{
            ExcelUtils<QqchWorkPlanningBuildPlan> util = new ExcelUtils<>(QqchWorkPlanningBuildPlan.class);
            List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlans = util.importExcel(file.getInputStream());
            return AjaxResult.success(qqchWorkPlanningBuildPlans);
        }catch (Exception e){
            throw new RuntimeException("导入失败！");
        }
    }
}
