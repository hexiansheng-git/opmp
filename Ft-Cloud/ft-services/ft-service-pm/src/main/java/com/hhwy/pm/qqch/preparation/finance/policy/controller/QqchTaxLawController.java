package com.hhwy.pm.qqch.preparation.finance.policy.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchTaxLaw;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchTaxLawService;
import com.hhwy.utils.validation.ValidationGroups;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-08-01 16:02:48
 * @remark 10.2.1税务监管环境概述-税法
 */
@Validated
@RestController
@RequestMapping("/qqchTaxLaw")
public class QqchTaxLawController extends BaseController {

    @Autowired
    private IQqchTaxLawService qqchTaxLawService;

    @PreAuthorize(hasPermi = "qqchTaxLaw:list")
    @GetMapping
    public AjaxResult getQqchTaxLaw(@Validated(ValidationGroups.Get.class) QqchTaxLaw qqchTaxLawParam) {
        QqchTaxLaw qqchTaxLaw = qqchTaxLawService.getQqchTaxLaw(qqchTaxLawParam);
        return AjaxResult.success(qqchTaxLaw);
    }

    @PreAuthorize(hasPermi = "qqchTaxLaw:list")
    @GetMapping("/list")
    public AjaxResult getQqchTaxLawList(@Validated(ValidationGroups.Select.class) QqchTaxLaw qqchTaxLawParam) {
        startPage();
        List<QqchTaxLaw> qqchTaxLawList = qqchTaxLawService.getQqchTaxLawList(qqchTaxLawParam);
        return getDataTableAjaxResult(qqchTaxLawList);
    }

    @PreAuthorize(hasPermi = "qqchTaxLaw:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTaxLaw(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchTaxLaw qqchTaxLawParam) {
        qqchTaxLawService.insertQqchTaxLaw(qqchTaxLawParam);
        return AjaxResult.success(qqchTaxLawParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxLaw:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTaxLawList(
        @Validated(ValidationGroups.Save.class) @RequestBody List<QqchTaxLaw> qqchTaxLawListParam) {
        qqchTaxLawService.insertQqchTaxLawList(qqchTaxLawListParam);
        return AjaxResult.success(qqchTaxLawListParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxLaw:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTaxLaw(
        @Validated(ValidationGroups.Update.class) @RequestBody QqchTaxLaw qqchTaxLawParam) {
        return toAjax(qqchTaxLawService.updateQqchTaxLaw(qqchTaxLawParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxLaw:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTaxLawList(
        @Validated(ValidationGroups.Update.class) @RequestBody List<QqchTaxLaw> qqchTaxLawListParam) {
        return toAjax(qqchTaxLawService.updateQqchTaxLawList(qqchTaxLawListParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxLaw:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTaxLaw(
        @Validated(ValidationGroups.Delete.class) @RequestBody QqchTaxLaw qqchTaxLawParam) {
        return toAjax(qqchTaxLawService.deleteQqchTaxLaw(qqchTaxLawParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxLaw:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTaxLawByPks(@PathVariable Long[] ids) {
        List<Long> qqchTaxLawPkList = Arrays.asList(ids);
        return toAjax(qqchTaxLawService.deleteQqchTaxLawByPks(qqchTaxLawPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTaxLaw qqchTaxLawParam) throws IOException {
        List<QqchTaxLaw> qqchTaxLawList = qqchTaxLawService.getQqchTaxLawList(qqchTaxLawParam);
        ExcelUtils<QqchTaxLaw> util = new ExcelUtils<>(QqchTaxLaw.class);
        util.exportExcel(response, qqchTaxLawList, DateUtils.getDate());
    }
}
