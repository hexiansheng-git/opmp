package com.hhwy.pm.qqch.preparation.technique.techManagePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchTopicResearchPlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchTopicResearchPlanService;
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
 * @date 2023-07-25 10:39:25
 * @remark 课题研究计划
 */
@Validated
@RestController
@RequestMapping("/qqchTopicResearchPlan")
public class QqchTopicResearchPlanController extends BaseController {

    @Autowired
    private IQqchTopicResearchPlanService qqchTopicResearchPlanService;


    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:list")
    @GetMapping
    public AjaxResult getQqchTopicResearchPlan(@Validated(ValidationGroups.Get.class) QqchTopicResearchPlan qqchTopicResearchPlanParam) {
        QqchTopicResearchPlan qqchTopicResearchPlan = qqchTopicResearchPlanService.getQqchTopicResearchPlan(qqchTopicResearchPlanParam);
        return AjaxResult.success(qqchTopicResearchPlan);
    }

    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchTopicResearchPlanList(@Validated(ValidationGroups.Select.class) QqchTopicResearchPlan qqchTopicResearchPlanParam) {
        startPage();
        List<QqchTopicResearchPlan> qqchTopicResearchPlanList = qqchTopicResearchPlanService.getQqchTopicResearchPlanList(qqchTopicResearchPlanParam);
        return getDataTableAjaxResult(qqchTopicResearchPlanList);
    }

    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTopicResearchPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchTopicResearchPlan qqchTopicResearchPlanParam) {
        qqchTopicResearchPlanService.insertQqchTopicResearchPlan(qqchTopicResearchPlanParam);
        return AjaxResult.success(qqchTopicResearchPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTopicResearchPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTopicResearchPlan> qqchTopicResearchPlanListParam) {
        qqchTopicResearchPlanService.insertQqchTopicResearchPlanList(qqchTopicResearchPlanListParam);
        return AjaxResult.success(qqchTopicResearchPlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTopicResearchPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchTopicResearchPlan qqchTopicResearchPlanParam) {
        return toAjax(qqchTopicResearchPlanService.updateQqchTopicResearchPlan(qqchTopicResearchPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTopicResearchPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTopicResearchPlan> qqchTopicResearchPlanListParam) {
        return toAjax(qqchTopicResearchPlanService.updateQqchTopicResearchPlanList(qqchTopicResearchPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTopicResearchPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTopicResearchPlan qqchTopicResearchPlanParam) {
        return toAjax(qqchTopicResearchPlanService.deleteQqchTopicResearchPlan(qqchTopicResearchPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTopicResearchPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchTopicResearchPlanPkList = Arrays.asList(ids);
        return toAjax(qqchTopicResearchPlanService.deleteQqchTopicResearchPlanByPks(qqchTopicResearchPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTopicResearchPlan qqchTopicResearchPlanParam) throws IOException {
        List<QqchTopicResearchPlan> qqchTopicResearchPlanList = qqchTopicResearchPlanService.getQqchTopicResearchPlanList(qqchTopicResearchPlanParam);
        ExcelUtils<QqchTopicResearchPlan> util = new ExcelUtils<>(QqchTopicResearchPlan.class);
        util.exportExcel(response, qqchTopicResearchPlanList, DateUtils.getDate());
    }
}
