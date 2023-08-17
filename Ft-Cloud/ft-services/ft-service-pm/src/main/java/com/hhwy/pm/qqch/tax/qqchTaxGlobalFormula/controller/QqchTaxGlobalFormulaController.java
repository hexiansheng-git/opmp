package com.hhwy.pm.qqch.tax.qqchTaxGlobalFormula.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.qqch.tax.qqchTaxGlobalFormula.service.IQqchTaxGlobalFormulaService;
import com.hhwy.pm.qqch.tax.qqchTaxGlobalFormula.domain.QqchTaxGlobalFormula;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-08-17 16:19:10
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchTaxGlobalFormula")
public class QqchTaxGlobalFormulaController extends BaseController{

    @Autowired
    private IQqchTaxGlobalFormulaService qqchTaxGlobalFormulaService;

                                                                                                                                                                                                                                                                                                                                                                                                                                                    

    @PreAuthorize(hasPermi = "qqchTaxGlobalFormula:list")
    @GetMapping
    public AjaxResult getQqchTaxGlobalFormula(@Validated(ValidationGroups.Get.class)  QqchTaxGlobalFormula qqchTaxGlobalFormulaParam){
        QqchTaxGlobalFormula qqchTaxGlobalFormula =  qqchTaxGlobalFormulaService.getQqchTaxGlobalFormula(qqchTaxGlobalFormulaParam);
        return AjaxResult.success(qqchTaxGlobalFormula);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobalFormula:list")
    @GetMapping("/list")
    public AjaxResult getQqchTaxGlobalFormulaList(@Validated(ValidationGroups.Select.class) QqchTaxGlobalFormula qqchTaxGlobalFormulaParam){
        startPage();
        List<QqchTaxGlobalFormula> qqchTaxGlobalFormulaList = qqchTaxGlobalFormulaService.getQqchTaxGlobalFormulaList(qqchTaxGlobalFormulaParam);
        return getDataTableAjaxResult(qqchTaxGlobalFormulaList);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobalFormula:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTaxGlobalFormula(@Validated(ValidationGroups.Save.class) @RequestBody QqchTaxGlobalFormula qqchTaxGlobalFormulaParam){
        qqchTaxGlobalFormulaService.insertQqchTaxGlobalFormula(qqchTaxGlobalFormulaParam);
        return AjaxResult.success(qqchTaxGlobalFormulaParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobalFormula:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTaxGlobalFormulaList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTaxGlobalFormula> qqchTaxGlobalFormulaListParam){
        qqchTaxGlobalFormulaService.insertQqchTaxGlobalFormulaList(qqchTaxGlobalFormulaListParam);
        return AjaxResult.success(qqchTaxGlobalFormulaListParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobalFormula:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTaxGlobalFormula(@Validated(ValidationGroups.Update.class) @RequestBody QqchTaxGlobalFormula qqchTaxGlobalFormulaParam){
        return toAjax(qqchTaxGlobalFormulaService.updateQqchTaxGlobalFormula(qqchTaxGlobalFormulaParam));
    }

            @PreAuthorize(hasPermi = "qqchTaxGlobalFormula:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchTaxGlobalFormulaList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTaxGlobalFormula> qqchTaxGlobalFormulaListParam){
            return toAjax(qqchTaxGlobalFormulaService.updateQqchTaxGlobalFormulaList(qqchTaxGlobalFormulaListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchTaxGlobalFormula:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTaxGlobalFormula(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTaxGlobalFormula qqchTaxGlobalFormulaParam){
        return toAjax(qqchTaxGlobalFormulaService.deleteQqchTaxGlobalFormula(qqchTaxGlobalFormulaParam));
    }

            @PreAuthorize(hasPermi = "qqchTaxGlobalFormula:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchTaxGlobalFormulaByPks(@PathVariable Long[] ids){
            List<Long> qqchTaxGlobalFormulaPkList = Arrays.asList(ids);
            return toAjax(qqchTaxGlobalFormulaService.deleteQqchTaxGlobalFormulaByPks(qqchTaxGlobalFormulaPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTaxGlobalFormula qqchTaxGlobalFormulaParam) throws IOException {
        List<QqchTaxGlobalFormula> qqchTaxGlobalFormulaList = qqchTaxGlobalFormulaService.getQqchTaxGlobalFormulaList(qqchTaxGlobalFormulaParam);
        ExcelUtils<QqchTaxGlobalFormula> util = new ExcelUtils<>(QqchTaxGlobalFormula.class);
        util.exportExcel(response, qqchTaxGlobalFormulaList, DateUtils.getDate());
    }
}
