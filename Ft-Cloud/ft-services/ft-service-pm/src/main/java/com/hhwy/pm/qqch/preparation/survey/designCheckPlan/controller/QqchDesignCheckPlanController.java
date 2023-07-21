package com.hhwy.pm.qqch.preparation.survey.designCheckPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.domain.QqchDesignCheckPlan;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.service.IQqchDesignCheckPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:48:41
 * @remark 2.3.3 设计成果验收计划
 */
@Validated
@RestController
@RequestMapping("/qqchDesignCheckPlan")
public class QqchDesignCheckPlanController extends BaseController {

    @Autowired
    private IQqchDesignCheckPlanService qqchDesignCheckPlanService;


    @PreAuthorize(hasPermi = "qqchDesignCheckPlan:list")
    @GetMapping
    public AjaxResult getQqchDesignCheckPlan(@Validated(ValidationGroups.Get.class) QqchDesignCheckPlan qqchDesignCheckPlanParam) {
        QqchDesignCheckPlan qqchDesignCheckPlan = qqchDesignCheckPlanService.getQqchDesignCheckPlan(qqchDesignCheckPlanParam);
        return AjaxResult.success(qqchDesignCheckPlan);
    }

    @PreAuthorize(hasPermi = "qqchDesignCheckPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchDesignCheckPlanList(@Validated(ValidationGroups.Select.class) QqchDesignCheckPlan qqchDesignCheckPlanParam) {
        startPage();
        List<QqchDesignCheckPlan> qqchDesignCheckPlanList = qqchDesignCheckPlanService.getQqchDesignCheckPlanList(qqchDesignCheckPlanParam);
        return getDataTableAjaxResult(qqchDesignCheckPlanList);
    }

    @PreAuthorize(hasPermi = "qqchDesignCheckPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchDesignCheckPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchDesignCheckPlan qqchDesignCheckPlanParam) {
        qqchDesignCheckPlanService.insertQqchDesignCheckPlan(qqchDesignCheckPlanParam);
        return AjaxResult.success(qqchDesignCheckPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchDesignCheckPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchDesignCheckPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchDesignCheckPlan> qqchDesignCheckPlanListParam) {
        qqchDesignCheckPlanService.insertQqchDesignCheckPlanList(qqchDesignCheckPlanListParam);
        return AjaxResult.success(qqchDesignCheckPlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchDesignCheckPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchDesignCheckPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchDesignCheckPlan qqchDesignCheckPlanParam) {
        return toAjax(qqchDesignCheckPlanService.updateQqchDesignCheckPlan(qqchDesignCheckPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchDesignCheckPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchDesignCheckPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchDesignCheckPlan> qqchDesignCheckPlanListParam) {
        return toAjax(qqchDesignCheckPlanService.updateQqchDesignCheckPlanList(qqchDesignCheckPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchDesignCheckPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchDesignCheckPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchDesignCheckPlan qqchDesignCheckPlanParam) {
        return toAjax(qqchDesignCheckPlanService.deleteQqchDesignCheckPlan(qqchDesignCheckPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchDesignCheckPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchDesignCheckPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchDesignCheckPlanPkList = Arrays.asList(ids);
        return toAjax(qqchDesignCheckPlanService.deleteQqchDesignCheckPlanByPks(qqchDesignCheckPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchDesignCheckPlan qqchDesignCheckPlanParam) throws IOException {
        List<QqchDesignCheckPlan> qqchDesignCheckPlanList = qqchDesignCheckPlanService.getQqchDesignCheckPlanList(qqchDesignCheckPlanParam);
        ExcelUtils<QqchDesignCheckPlan> util = new ExcelUtils<>(QqchDesignCheckPlan.class);
        util.exportExcel(response, qqchDesignCheckPlanList, DateUtils.getDate());
    }
}
