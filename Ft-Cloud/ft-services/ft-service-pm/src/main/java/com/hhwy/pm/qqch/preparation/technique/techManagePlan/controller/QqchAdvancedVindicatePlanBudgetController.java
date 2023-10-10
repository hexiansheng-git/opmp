package com.hhwy.pm.qqch.preparation.technique.techManagePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAdvancedVindicatePlanBudget;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchAdvancedVindicatePlanBudgetService;
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
 * @date 2023-07-27 15:51:20
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchAdvancedVindicatePlanBudget")
public class QqchAdvancedVindicatePlanBudgetController extends BaseController {

    @Autowired
    private IQqchAdvancedVindicatePlanBudgetService qqchAdvancedVindicatePlanBudgetService;


//    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlanBudget:list")
    @GetMapping
    public AjaxResult getQqchAdvancedVindicatePlanBudget(@Validated(ValidationGroups.Get.class) QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudgetParam) {
        QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget = qqchAdvancedVindicatePlanBudgetService.getQqchAdvancedVindicatePlanBudget(qqchAdvancedVindicatePlanBudgetParam);
        return AjaxResult.success(qqchAdvancedVindicatePlanBudget);
    }

//    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlanBudget:list")
    @GetMapping("/list")
    public AjaxResult getQqchAdvancedVindicatePlanBudgetList(@Validated(ValidationGroups.Select.class) QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudgetParam) {
        startPage();
        List<QqchAdvancedVindicatePlanBudget> qqchAdvancedVindicatePlanBudgetList = qqchAdvancedVindicatePlanBudgetService.getQqchAdvancedVindicatePlanBudgetList(qqchAdvancedVindicatePlanBudgetParam);
        return getDataTableAjaxResult(qqchAdvancedVindicatePlanBudgetList);
    }

//    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlanBudget:add")
    @PostMapping("/add")
    public AjaxResult insertQqchAdvancedVindicatePlanBudget(@Validated(ValidationGroups.Save.class) @RequestBody QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudgetParam) {
        qqchAdvancedVindicatePlanBudgetService.insertQqchAdvancedVindicatePlanBudget(qqchAdvancedVindicatePlanBudgetParam);
        return AjaxResult.success(qqchAdvancedVindicatePlanBudgetParam);
    }

//    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlanBudget:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchAdvancedVindicatePlanBudgetList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchAdvancedVindicatePlanBudget> qqchAdvancedVindicatePlanBudgetListParam) {
        qqchAdvancedVindicatePlanBudgetService.insertQqchAdvancedVindicatePlanBudgetList(qqchAdvancedVindicatePlanBudgetListParam);
        return AjaxResult.success(qqchAdvancedVindicatePlanBudgetListParam);
    }

//    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlanBudget:update")
    @PostMapping("/update")
    public AjaxResult updateQqchAdvancedVindicatePlanBudget(@Validated(ValidationGroups.Update.class) @RequestBody QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudgetParam) {
        return toAjax(qqchAdvancedVindicatePlanBudgetService.updateQqchAdvancedVindicatePlanBudget(qqchAdvancedVindicatePlanBudgetParam));
    }

//    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlanBudget:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchAdvancedVindicatePlanBudgetList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchAdvancedVindicatePlanBudget> qqchAdvancedVindicatePlanBudgetListParam) {
        return toAjax(qqchAdvancedVindicatePlanBudgetService.updateQqchAdvancedVindicatePlanBudgetList(qqchAdvancedVindicatePlanBudgetListParam));
    }

//    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlanBudget:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchAdvancedVindicatePlanBudget(@Validated(ValidationGroups.Delete.class) @RequestBody QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudgetParam) {
        return toAjax(qqchAdvancedVindicatePlanBudgetService.deleteQqchAdvancedVindicatePlanBudget(qqchAdvancedVindicatePlanBudgetParam));
    }

//    @PreAuthorize(hasPermi = "qqchAdvancedVindicatePlanBudget:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchAdvancedVindicatePlanBudgetByPks(@PathVariable Long[] ids) {
        List<Long> qqchAdvancedVindicatePlanBudgetPkList = Arrays.asList(ids);
        return toAjax(qqchAdvancedVindicatePlanBudgetService.deleteQqchAdvancedVindicatePlanBudgetByPks(qqchAdvancedVindicatePlanBudgetPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudgetParam) throws IOException {
        List<QqchAdvancedVindicatePlanBudget> qqchAdvancedVindicatePlanBudgetList = qqchAdvancedVindicatePlanBudgetService.getQqchAdvancedVindicatePlanBudgetList(qqchAdvancedVindicatePlanBudgetParam);
        ExcelUtils<QqchAdvancedVindicatePlanBudget> util = new ExcelUtils<>(QqchAdvancedVindicatePlanBudget.class);
        util.exportExcel(response, qqchAdvancedVindicatePlanBudgetList, DateUtils.getDate());
    }
}
