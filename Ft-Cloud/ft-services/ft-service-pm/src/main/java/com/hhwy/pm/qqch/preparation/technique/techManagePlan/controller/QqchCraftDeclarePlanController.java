package com.hhwy.pm.qqch.preparation.technique.techManagePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchCraftDeclarePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchCraftDeclarePlanService;
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
 * @date 2023-07-25 10:40:02
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchCraftDeclarePlan")
public class QqchCraftDeclarePlanController extends BaseController {

    @Autowired
    private IQqchCraftDeclarePlanService qqchCraftDeclarePlanService;


    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:list")
    @GetMapping
    public AjaxResult getQqchCraftDeclarePlan(@Validated(ValidationGroups.Get.class) QqchCraftDeclarePlan qqchCraftDeclarePlanParam) {
        QqchCraftDeclarePlan qqchCraftDeclarePlan = qqchCraftDeclarePlanService.getQqchCraftDeclarePlan(qqchCraftDeclarePlanParam);
        return AjaxResult.success(qqchCraftDeclarePlan);
    }

    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchCraftDeclarePlanList(@Validated(ValidationGroups.Select.class) QqchCraftDeclarePlan qqchCraftDeclarePlanParam) {
        startPage();
        List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList = qqchCraftDeclarePlanService.getQqchCraftDeclarePlanList(qqchCraftDeclarePlanParam);
        return getDataTableAjaxResult(qqchCraftDeclarePlanList);
    }

    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchCraftDeclarePlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchCraftDeclarePlan qqchCraftDeclarePlanParam) {
        qqchCraftDeclarePlanService.insertQqchCraftDeclarePlan(qqchCraftDeclarePlanParam);
        return AjaxResult.success(qqchCraftDeclarePlanParam);
    }

    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchCraftDeclarePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchCraftDeclarePlan> qqchCraftDeclarePlanListParam) {
        qqchCraftDeclarePlanService.insertQqchCraftDeclarePlanList(qqchCraftDeclarePlanListParam);
        return AjaxResult.success(qqchCraftDeclarePlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchCraftDeclarePlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchCraftDeclarePlan qqchCraftDeclarePlanParam) {
        return toAjax(qqchCraftDeclarePlanService.updateQqchCraftDeclarePlan(qqchCraftDeclarePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchCraftDeclarePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchCraftDeclarePlan> qqchCraftDeclarePlanListParam) {
        return toAjax(qqchCraftDeclarePlanService.updateQqchCraftDeclarePlanList(qqchCraftDeclarePlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchCraftDeclarePlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchCraftDeclarePlan qqchCraftDeclarePlanParam) {
        return toAjax(qqchCraftDeclarePlanService.deleteQqchCraftDeclarePlan(qqchCraftDeclarePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchCraftDeclarePlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchCraftDeclarePlanPkList = Arrays.asList(ids);
        return toAjax(qqchCraftDeclarePlanService.deleteQqchCraftDeclarePlanByPks(qqchCraftDeclarePlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchCraftDeclarePlan qqchCraftDeclarePlanParam) throws IOException {
        List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList = qqchCraftDeclarePlanService.getQqchCraftDeclarePlanList(qqchCraftDeclarePlanParam);
        ExcelUtils<QqchCraftDeclarePlan> util = new ExcelUtils<>(QqchCraftDeclarePlan.class);
        util.exportExcel(response, qqchCraftDeclarePlanList, DateUtils.getDate());
    }
}
