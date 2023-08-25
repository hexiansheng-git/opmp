package com.hhwy.pm.qqch.tax.qqchTaxGlobal.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobal;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobalFormula;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.IQqchTaxGlobalFormulaService;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.IQqchTaxGlobalService;
import com.hhwy.utils.excel.FtExcelEnum;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-17 16:19:06
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchTaxGlobal")
public class QqchTaxGlobalController extends BaseController {

    @Resource
    private IQqchTaxGlobalService qqchTaxGlobalService;

    @Resource
    private IQqchTaxGlobalFormulaService taxGlobalFormulaService;


    @PreAuthorize(hasPermi = "qqchTaxGlobal:list")
    @GetMapping
    public AjaxResult getQqchTaxGlobal(@Validated(ValidationGroups.Get.class) QqchTaxGlobal qqchTaxGlobalParam) {
        QqchTaxGlobal qqchTaxGlobal = qqchTaxGlobalService.getQqchTaxGlobal(qqchTaxGlobalParam);
        return AjaxResult.success(qqchTaxGlobal);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:list")
    @GetMapping("/list")
    public AjaxResult list(@Validated(ValidationGroups.Select.class) QqchTaxGlobal dto) {
        CompileEntity<List<QqchTaxGlobal>> qqchTaxGlobalList = null;
        try {
            qqchTaxGlobalList = qqchTaxGlobalService.list(dto.dealListDto() == null ? new QqchTaxGlobal() : dto.dealListDto());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success(qqchTaxGlobalList);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody CompileEntity<List<QqchTaxGlobal>> params) {
        qqchTaxGlobalService.save(params.dealSaveDto());
        return AjaxResult.success(params);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTaxGlobalList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTaxGlobal> qqchTaxGlobalListParam) {
        qqchTaxGlobalService.insertQqchTaxGlobalList(qqchTaxGlobalListParam);
        return AjaxResult.success(qqchTaxGlobalListParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTaxGlobal(@Validated(ValidationGroups.Update.class) @RequestBody QqchTaxGlobal qqchTaxGlobalParam) {
        return toAjax(qqchTaxGlobalService.updateQqchTaxGlobal(qqchTaxGlobalParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTaxGlobalList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTaxGlobal> qqchTaxGlobalListParam) {
        return toAjax(qqchTaxGlobalService.updateQqchTaxGlobalList(qqchTaxGlobalListParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTaxGlobal(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTaxGlobal qqchTaxGlobalParam) {
        return toAjax(qqchTaxGlobalService.deleteQqchTaxGlobal(qqchTaxGlobalParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTaxGlobalByPks(@PathVariable Long[] ids) {
        List<Long> qqchTaxGlobalPkList = Arrays.asList(ids);
        return toAjax(qqchTaxGlobalService.deleteQqchTaxGlobalByPks(qqchTaxGlobalPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTaxGlobal qqchTaxGlobalParam) throws IOException {
        List<QqchTaxGlobal> qqchTaxGlobalList = qqchTaxGlobalService.getQqchTaxGlobalList(qqchTaxGlobalParam);
        FtExcelUtil<QqchTaxGlobal> util = new FtExcelUtil<>(QqchTaxGlobal.class);
        util.exportWithTemplate(response, qqchTaxGlobalList, 3, FtExcelEnum.QQCH_TAX_GLOBAL.getTemplateName(), "sheet1");
    }


    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) {

        try {
            FtExcelUtil<QqchTaxGlobal> excelUtil = new FtExcelUtil<>(QqchTaxGlobal.class);
            List<QqchTaxGlobal> qqchTaxGlobals = excelUtil.importTreeExcel(file.getInputStream(), 3);
            return AjaxResult.success(qqchTaxGlobals);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }



    @PostMapping("/saveFormula")
    public AjaxResult saveFormula(QqchTaxGlobalFormula param) {
        taxGlobalFormulaService.save(param.dealSaveDto());
        return AjaxResult.success(param);
    }

    @GetMapping("/getFormula")
    public AjaxResult getFormula(QqchTaxGlobalFormula param) {
        CompileEntity<QqchTaxGlobalFormula> qqchTaxGlobalFormula = taxGlobalFormulaService.getFormula(param.dealListDto());
        return AjaxResult.success(qqchTaxGlobalFormula);
    }
    @GetMapping("/getGlobalByFormula")
    public AjaxResult getGlobalByFormula(QqchTaxGlobalFormula param) {
        List<QqchTaxGlobal> qqchTaxGlobalFormula = taxGlobalFormulaService.getGlobalByFormula(param.dealListDto());
        return AjaxResult.success(qqchTaxGlobalFormula);
    }
    
    
}
