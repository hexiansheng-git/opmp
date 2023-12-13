package com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyEquPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.service.IQqchSurveyEquPlanService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
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
 * @date 2023-07-25 11:09:40
 * @remark  2.1.3 勘察设计队伍配置 ---设备策划
 */
@Validated
@RestController
@RequestMapping("/qqchSurveyEquPlan")
public class QqchSurveyEquPlanController extends BaseController {

    @Autowired
    private IQqchSurveyEquPlanService qqchSurveyEquPlanService;


    @PreAuthorize(hasPermi = "qqchSurveyEquPlan:list")
    @GetMapping
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计经营模式策划", name = "2.1.3勘察设计队伍配置" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchSurveyEquPlan(@Validated(ValidationGroups.Get.class) QqchSurveyEquPlan qqchSurveyEquPlanParam) {
        QqchSurveyEquPlan qqchSurveyEquPlan = qqchSurveyEquPlanService.getQqchSurveyEquPlan(qqchSurveyEquPlanParam);
        return AjaxResult.success(qqchSurveyEquPlan);
    }

    @PreAuthorize(hasPermi = "qqchSurveyEquPlan:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计经营模式策划", name = "2.1.3勘察设计队伍配置" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchSurveyEquPlanList(@Validated(ValidationGroups.Select.class) QqchSurveyEquPlan qqchSurveyEquPlanParam) {
        startPage();
        List<QqchSurveyEquPlan> qqchSurveyEquPlanList = qqchSurveyEquPlanService.getQqchSurveyEquPlanList(qqchSurveyEquPlanParam);
        return getDataTableAjaxResult(qqchSurveyEquPlanList);
    }

    @PreAuthorize(hasPermi = "qqchSurveyEquPlan:add")
    @PostMapping("/add")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计经营模式策划", name = "2.1.3勘察设计队伍配置" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchSurveyEquPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyEquPlan qqchSurveyEquPlanParam) {
        qqchSurveyEquPlanService.insertQqchSurveyEquPlan(qqchSurveyEquPlanParam);
        return AjaxResult.success(qqchSurveyEquPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchSurveyEquPlan:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计经营模式策划", name = "2.1.3勘察设计队伍配置" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchSurveyEquPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSurveyEquPlan> qqchSurveyEquPlanListParam) {
        qqchSurveyEquPlanService.insertQqchSurveyEquPlanList(qqchSurveyEquPlanListParam);
        return AjaxResult.success(qqchSurveyEquPlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchSurveyEquPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSurveyEquPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchSurveyEquPlan qqchSurveyEquPlanParam) {
        return toAjax(qqchSurveyEquPlanService.updateQqchSurveyEquPlan(qqchSurveyEquPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyEquPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSurveyEquPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSurveyEquPlan> qqchSurveyEquPlanListParam) {
        return toAjax(qqchSurveyEquPlanService.updateQqchSurveyEquPlanList(qqchSurveyEquPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyEquPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSurveyEquPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSurveyEquPlan qqchSurveyEquPlanParam) {
        return toAjax(qqchSurveyEquPlanService.deleteQqchSurveyEquPlan(qqchSurveyEquPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyEquPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSurveyEquPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchSurveyEquPlanPkList = Arrays.asList(ids);
        return toAjax(qqchSurveyEquPlanService.deleteQqchSurveyEquPlanByPks(qqchSurveyEquPlanPkList));
    }

    @GetMapping("/export")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计经营模式策划", name = "2.1.3勘察设计队伍配置" ,businessType = CustomBusinessType.EXPORT)
    public void export(HttpServletResponse response, QqchSurveyEquPlan qqchSurveyEquPlanParam) throws IOException {
        List<QqchSurveyEquPlan> qqchSurveyEquPlanList = qqchSurveyEquPlanService.getQqchSurveyEquPlanList(qqchSurveyEquPlanParam);
        ExcelUtils<QqchSurveyEquPlan> util = new ExcelUtils<>(QqchSurveyEquPlan.class);
        util.exportExcel(response, qqchSurveyEquPlanList, DateUtils.getDate());
    }
}
