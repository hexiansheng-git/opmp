package com.hhwy.pm.qqch.sgch.sche.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheFactors;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheFactorsService;
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
 * @date 2023-07-31 11:22:52
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchScheFactors")
public class QqchScheFactorsController extends BaseController {

    @Autowired
    private IQqchScheFactorsService qqchScheFactorsService;


    @PreAuthorize(hasPermi = "qqchScheFactors:list")
    @GetMapping
    public AjaxResult getQqchScheFactors(@Validated(ValidationGroups.Get.class) QqchScheFactors qqchScheFactorsParam) {
        QqchScheFactors qqchScheFactors = qqchScheFactorsService.getQqchScheFactors(qqchScheFactorsParam);
        return AjaxResult.success(qqchScheFactors);
    }

    @PreAuthorize(hasPermi = "qqchScheFactors:list")
    @GetMapping("/list")
    public AjaxResult getQqchScheFactorsList(@Validated(ValidationGroups.Select.class) QqchScheFactors qqchScheFactorsParam) {
        startPage();
        List<QqchScheFactors> qqchScheFactorsList = qqchScheFactorsService.getQqchScheFactorsList(qqchScheFactorsParam);
        return getDataTableAjaxResult(qqchScheFactorsList);
    }

    @PreAuthorize(hasPermi = "qqchScheFactors:add")
    @PostMapping("/add")
    public AjaxResult insertQqchScheFactors(@Validated(ValidationGroups.Save.class) @RequestBody QqchScheFactors qqchScheFactorsParam) {
        qqchScheFactorsService.insertQqchScheFactors(qqchScheFactorsParam);
        return AjaxResult.success(qqchScheFactorsParam);
    }

    @PreAuthorize(hasPermi = "qqchScheFactors:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchScheFactorsList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchScheFactors> qqchScheFactorsListParam) {
        qqchScheFactorsService.insertQqchScheFactorsList(qqchScheFactorsListParam);
        return AjaxResult.success(qqchScheFactorsListParam);
    }

    @PreAuthorize(hasPermi = "qqchScheFactors:update")
    @PostMapping("/update")
    public AjaxResult updateQqchScheFactors(@Validated(ValidationGroups.Update.class) @RequestBody QqchScheFactors qqchScheFactorsParam) {
        return toAjax(qqchScheFactorsService.updateQqchScheFactors(qqchScheFactorsParam));
    }

    @PreAuthorize(hasPermi = "qqchScheFactors:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchScheFactorsList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchScheFactors> qqchScheFactorsListParam) {
        return toAjax(qqchScheFactorsService.updateQqchScheFactorsList(qqchScheFactorsListParam));
    }

    @PreAuthorize(hasPermi = "qqchScheFactors:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchScheFactors(@Validated(ValidationGroups.Delete.class) @RequestBody QqchScheFactors qqchScheFactorsParam) {
        return toAjax(qqchScheFactorsService.deleteQqchScheFactors(qqchScheFactorsParam));
    }

    @PreAuthorize(hasPermi = "qqchScheFactors:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchScheFactorsByPks(@PathVariable Long[] ids) {
        List<Long> qqchScheFactorsPkList = Arrays.asList(ids);
        return toAjax(qqchScheFactorsService.deleteQqchScheFactorsByPks(qqchScheFactorsPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchScheFactors qqchScheFactorsParam) throws IOException {
        List<QqchScheFactors> qqchScheFactorsList = qqchScheFactorsService.getQqchScheFactorsList(qqchScheFactorsParam);
        ExcelUtils<QqchScheFactors> util = new ExcelUtils<>(QqchScheFactors.class);
        util.exportExcel(response, qqchScheFactorsList, DateUtils.getDate());
    }
}
