package com.hhwy.pm.qqch.preparation.technique.techManagePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAppInnovatePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchAppInnovatePlanService;
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
 * @date 2023-07-25 10:39:47
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchAppInnovatePlan")
public class QqchAppInnovatePlanController extends BaseController {

    @Autowired
    private IQqchAppInnovatePlanService qqchAppInnovatePlanService;


    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:list")
    @GetMapping
    public AjaxResult getQqchAppInnovatePlan(@Validated(ValidationGroups.Get.class) QqchAppInnovatePlan qqchAppInnovatePlanParam) {
        QqchAppInnovatePlan qqchAppInnovatePlan = qqchAppInnovatePlanService.getQqchAppInnovatePlan(qqchAppInnovatePlanParam);
        return AjaxResult.success(qqchAppInnovatePlan);
    }

    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchAppInnovatePlanList(@Validated(ValidationGroups.Select.class) QqchAppInnovatePlan qqchAppInnovatePlanParam) {
        startPage();
        List<QqchAppInnovatePlan> qqchAppInnovatePlanList = qqchAppInnovatePlanService.getQqchAppInnovatePlanList(qqchAppInnovatePlanParam);
        return getDataTableAjaxResult(qqchAppInnovatePlanList);
    }

    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchAppInnovatePlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchAppInnovatePlan qqchAppInnovatePlanParam) {
        qqchAppInnovatePlanService.insertQqchAppInnovatePlan(qqchAppInnovatePlanParam);
        return AjaxResult.success(qqchAppInnovatePlanParam);
    }

    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchAppInnovatePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchAppInnovatePlan> qqchAppInnovatePlanListParam) {
        qqchAppInnovatePlanService.insertQqchAppInnovatePlanList(qqchAppInnovatePlanListParam);
        return AjaxResult.success(qqchAppInnovatePlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchAppInnovatePlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchAppInnovatePlan qqchAppInnovatePlanParam) {
        return toAjax(qqchAppInnovatePlanService.updateQqchAppInnovatePlan(qqchAppInnovatePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchAppInnovatePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchAppInnovatePlan> qqchAppInnovatePlanListParam) {
        return toAjax(qqchAppInnovatePlanService.updateQqchAppInnovatePlanList(qqchAppInnovatePlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchAppInnovatePlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchAppInnovatePlan qqchAppInnovatePlanParam) {
        return toAjax(qqchAppInnovatePlanService.deleteQqchAppInnovatePlan(qqchAppInnovatePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchAppInnovatePlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchAppInnovatePlanPkList = Arrays.asList(ids);
        return toAjax(qqchAppInnovatePlanService.deleteQqchAppInnovatePlanByPks(qqchAppInnovatePlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchAppInnovatePlan qqchAppInnovatePlanParam) throws IOException {
        List<QqchAppInnovatePlan> qqchAppInnovatePlanList = qqchAppInnovatePlanService.getQqchAppInnovatePlanList(qqchAppInnovatePlanParam);
        ExcelUtils<QqchAppInnovatePlan> util = new ExcelUtils<>(QqchAppInnovatePlan.class);
        util.exportExcel(response, qqchAppInnovatePlanList, DateUtils.getDate());
    }
}
