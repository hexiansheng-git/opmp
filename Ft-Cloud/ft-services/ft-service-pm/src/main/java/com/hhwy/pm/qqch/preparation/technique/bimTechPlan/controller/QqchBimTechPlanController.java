package com.hhwy.pm.qqch.preparation.technique.bimTechPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.domain.QqchBimTechPlan;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.service.IQqchBimTechPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:45:57
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchBimTechPlan")
public class QqchBimTechPlanController extends BaseController {

    @Autowired
    private IQqchBimTechPlanService qqchBimTechPlanService;


    @PreAuthorize(hasPermi = "qqchBimTechPlan:list")
    @GetMapping
    public AjaxResult getQqchBimTechPlan(@Validated(ValidationGroups.Get.class) QqchBimTechPlan qqchBimTechPlanParam) {
        QqchBimTechPlan qqchBimTechPlan = qqchBimTechPlanService.getQqchBimTechPlan(qqchBimTechPlanParam);
        return AjaxResult.success(qqchBimTechPlan);
    }

    @PreAuthorize(hasPermi = "qqchBimTechPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchBimTechPlanList(@Validated(ValidationGroups.Select.class) QqchBimTechPlan qqchBimTechPlanParam) {
        startPage();
        List<QqchBimTechPlan> qqchBimTechPlanList = qqchBimTechPlanService.getQqchBimTechPlanList(qqchBimTechPlanParam);
        return getDataTableAjaxResult(qqchBimTechPlanList);
    }

    @PreAuthorize(hasPermi = "qqchBimTechPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchBimTechPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchBimTechPlan qqchBimTechPlanParam) {
        qqchBimTechPlanService.insertQqchBimTechPlan(qqchBimTechPlanParam);
        return AjaxResult.success(qqchBimTechPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchBimTechPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchBimTechPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchBimTechPlan> qqchBimTechPlanListParam) {
        qqchBimTechPlanService.insertQqchBimTechPlanList(qqchBimTechPlanListParam);
        return AjaxResult.success(qqchBimTechPlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchBimTechPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchBimTechPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchBimTechPlan qqchBimTechPlanParam) {
        return toAjax(qqchBimTechPlanService.updateQqchBimTechPlan(qqchBimTechPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchBimTechPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchBimTechPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchBimTechPlan> qqchBimTechPlanListParam) {
        return toAjax(qqchBimTechPlanService.updateQqchBimTechPlanList(qqchBimTechPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchBimTechPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchBimTechPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchBimTechPlan qqchBimTechPlanParam) {
        return toAjax(qqchBimTechPlanService.deleteQqchBimTechPlan(qqchBimTechPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchBimTechPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchBimTechPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchBimTechPlanPkList = Arrays.asList(ids);
        return toAjax(qqchBimTechPlanService.deleteQqchBimTechPlanByPks(qqchBimTechPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchBimTechPlan qqchBimTechPlanParam) throws IOException {
        List<QqchBimTechPlan> qqchBimTechPlanList = qqchBimTechPlanService.getQqchBimTechPlanList(qqchBimTechPlanParam);
        ExcelUtils<QqchBimTechPlan> util = new ExcelUtils<>(QqchBimTechPlan.class);
        util.exportExcel(response, qqchBimTechPlanList, DateUtils.getDate());
    }
}
