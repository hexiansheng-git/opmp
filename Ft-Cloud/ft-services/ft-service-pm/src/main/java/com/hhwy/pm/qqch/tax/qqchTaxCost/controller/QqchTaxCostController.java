package com.hhwy.pm.qqch.tax.qqchTaxCost.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCost;
import com.hhwy.pm.qqch.tax.qqchTaxCost.service.IQqchTaxCostService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:14
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchTaxCost")
public class QqchTaxCostController extends BaseController {

    @Autowired
    private IQqchTaxCostService qqchTaxCostService;


    @PreAuthorize(hasPermi = "qqchTaxCost:list")
    @GetMapping
    public AjaxResult getQqchTaxCost(@Validated(ValidationGroups.Get.class) QqchTaxCost qqchTaxCostParam) {
        QqchTaxCost qqchTaxCost = qqchTaxCostService.getQqchTaxCost(qqchTaxCostParam);
        return AjaxResult.success(qqchTaxCost);
    }

    @PreAuthorize(hasPermi = "qqchTaxCost:list")
    @GetMapping("/list")
    public AjaxResult getQqchTaxCostList(@Validated(ValidationGroups.Select.class) QqchTaxCost qqchTaxCostParam) {
        startPage();
        List<QqchTaxCost> qqchTaxCostList = qqchTaxCostService.getQqchTaxCostList(qqchTaxCostParam);
        return getDataTableAjaxResult(qqchTaxCostList);
    }

    @PreAuthorize(hasPermi = "qqchTaxCost:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTaxCost(@Validated(ValidationGroups.Save.class) @RequestBody QqchTaxCost qqchTaxCostParam) {
        qqchTaxCostService.insertQqchTaxCost(qqchTaxCostParam);
        return AjaxResult.success(qqchTaxCostParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxCost:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTaxCostList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTaxCost> qqchTaxCostListParam) {
        qqchTaxCostService.insertQqchTaxCostList(qqchTaxCostListParam);
        return AjaxResult.success(qqchTaxCostListParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxCost:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTaxCost(@Validated(ValidationGroups.Update.class) @RequestBody QqchTaxCost qqchTaxCostParam) {
        return toAjax(qqchTaxCostService.updateQqchTaxCost(qqchTaxCostParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxCost:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTaxCostList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTaxCost> qqchTaxCostListParam) {
        return toAjax(qqchTaxCostService.updateQqchTaxCostList(qqchTaxCostListParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxCost:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTaxCost(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTaxCost qqchTaxCostParam) {
        return toAjax(qqchTaxCostService.deleteQqchTaxCost(qqchTaxCostParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxCost:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTaxCostByPks(@PathVariable Long[] ids) {
        List<Long> qqchTaxCostPkList = Arrays.asList(ids);
        return toAjax(qqchTaxCostService.deleteQqchTaxCostByPks(qqchTaxCostPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTaxCost qqchTaxCostParam) throws IOException {
        List<QqchTaxCost> qqchTaxCostList = qqchTaxCostService.getQqchTaxCostList(qqchTaxCostParam);
        ExcelUtils<QqchTaxCost> util = new ExcelUtils<>(QqchTaxCost.class);
        util.exportExcel(response, qqchTaxCostList, DateUtils.getDate());
    }
}
