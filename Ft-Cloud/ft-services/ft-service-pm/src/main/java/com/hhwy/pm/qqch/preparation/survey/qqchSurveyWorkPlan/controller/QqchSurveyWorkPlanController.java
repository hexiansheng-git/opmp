package com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.domain.QqchSurveyWorkPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.service.IQqchSurveyWorkPlanService;
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
 * @date 2023-07-20 11:49:55
 * @remark 2.2 勘察设计工作计划
 */
@Validated
@RestController
@RequestMapping("/qqchSurveyWorkPlan")
public class QqchSurveyWorkPlanController extends BaseController {

    @Autowired
    private IQqchSurveyWorkPlanService qqchSurveyWorkPlanService;


    @PreAuthorize(hasPermi = "qqchSurveyWorkPlan:list")
    @GetMapping
    public AjaxResult getQqchSurveyWorkPlan(@Validated(ValidationGroups.Get.class) QqchSurveyWorkPlan qqchSurveyWorkPlanParam) {
        QqchSurveyWorkPlan qqchSurveyWorkPlan = qqchSurveyWorkPlanService.getQqchSurveyWorkPlan(qqchSurveyWorkPlanParam);
        return AjaxResult.success(qqchSurveyWorkPlan);
    }

    /**
     *  列表接口
     *
     * @param qqchSurveyWorkPlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyWorkPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchSurveyWorkPlanList(@Validated(ValidationGroups.Select.class) QqchSurveyWorkPlan qqchSurveyWorkPlanParam) {
        //startPage();
        List<QqchSurveyWorkPlan> qqchSurveyWorkPlanList = qqchSurveyWorkPlanService.getQqchSurveyWorkPlanList(qqchSurveyWorkPlanParam);
        return getDataTableAjaxResult(qqchSurveyWorkPlanList);
    }

    @PreAuthorize(hasPermi = "qqchSurveyWorkPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSurveyWorkPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyWorkPlan qqchSurveyWorkPlanParam) {
        qqchSurveyWorkPlanService.insertQqchSurveyWorkPlan(qqchSurveyWorkPlanParam);
        return AjaxResult.success(qqchSurveyWorkPlanParam);
    }

    /**
     *  批增
     *
     * @param qqchSurveyWorkPlanListParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyWorkPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSurveyWorkPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSurveyWorkPlan> qqchSurveyWorkPlanListParam) {
        qqchSurveyWorkPlanService.insertQqchSurveyWorkPlanList(qqchSurveyWorkPlanListParam);
        return AjaxResult.success(qqchSurveyWorkPlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchSurveyWorkPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSurveyWorkPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchSurveyWorkPlan qqchSurveyWorkPlanParam) {
        return toAjax(qqchSurveyWorkPlanService.updateQqchSurveyWorkPlan(qqchSurveyWorkPlanParam));
    }


    @PreAuthorize(hasPermi = "qqchSurveyWorkPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSurveyWorkPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSurveyWorkPlan> qqchSurveyWorkPlanListParam) {
        return toAjax(qqchSurveyWorkPlanService.updateQqchSurveyWorkPlanList(qqchSurveyWorkPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyWorkPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSurveyWorkPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSurveyWorkPlan qqchSurveyWorkPlanParam) {
        return toAjax(qqchSurveyWorkPlanService.deleteQqchSurveyWorkPlan(qqchSurveyWorkPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyWorkPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSurveyWorkPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchSurveyWorkPlanPkList = Arrays.asList(ids);
        return toAjax(qqchSurveyWorkPlanService.deleteQqchSurveyWorkPlanByPks(qqchSurveyWorkPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSurveyWorkPlan qqchSurveyWorkPlanParam) throws IOException {
        List<QqchSurveyWorkPlan> qqchSurveyWorkPlanList = qqchSurveyWorkPlanService.getQqchSurveyWorkPlanList(qqchSurveyWorkPlanParam);
        ExcelUtils<QqchSurveyWorkPlan> util = new ExcelUtils<>(QqchSurveyWorkPlan.class);
        util.exportExcel(response, qqchSurveyWorkPlanList, DateUtils.getDate());
    }
}
