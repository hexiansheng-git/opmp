package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonScheme;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author han
 * @date 2023-07-07 18:35:38
 * @remark 重大设计方案比选-方案
 */
@Validated
@RestController
@RequestMapping("/qqchComparisonScheme")
public class QqchComparisonSchemeController extends BaseController {

    @Autowired
    private IQqchComparisonSchemeService qqchComparisonSchemeService;


    @PreAuthorize(hasPermi = "qqchComparisonScheme:list")
    @GetMapping
    public AjaxResult getQqchComparisonScheme(@Validated(ValidationGroups.Get.class) @RequestBody QqchComparisonScheme qqchComparisonSchemeParam) {
        QqchComparisonScheme qqchComparisonScheme = qqchComparisonSchemeService.getQqchComparisonScheme(qqchComparisonSchemeParam);
        return AjaxResult.success(qqchComparisonScheme);
    }

    @PreAuthorize(hasPermi = "qqchComparisonScheme:list")
    @GetMapping("/list")
    public AjaxResult getQqchComparisonSchemeList(@Validated(ValidationGroups.Select.class) @RequestBody QqchComparisonScheme qqchComparisonSchemeParam) {
        startPage();
        List<QqchComparisonScheme> qqchComparisonSchemeList = qqchComparisonSchemeService.getQqchComparisonSchemeList(qqchComparisonSchemeParam);
        return getDataTableAjaxResult(qqchComparisonSchemeList);
    }

    @PreAuthorize(hasPermi = "qqchComparisonScheme:add")
    @PostMapping("/add")
    public AjaxResult insertQqchComparisonScheme(@Validated(ValidationGroups.Save.class) @RequestBody QqchComparisonScheme qqchComparisonSchemeParam) {
        qqchComparisonSchemeService.insertQqchComparisonScheme(qqchComparisonSchemeParam);
        return AjaxResult.success(qqchComparisonSchemeParam);
    }

    @PreAuthorize(hasPermi = "qqchComparisonScheme:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchComparisonSchemeList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchComparisonScheme> qqchComparisonSchemeListParam) {
        qqchComparisonSchemeService.insertQqchComparisonSchemeList(qqchComparisonSchemeListParam);
        return AjaxResult.success(qqchComparisonSchemeListParam);
    }

    @PreAuthorize(hasPermi = "qqchComparisonScheme:update")
    @PostMapping("/update")
    public AjaxResult updateQqchComparisonScheme(@Validated(ValidationGroups.Update.class) @RequestBody QqchComparisonScheme qqchComparisonSchemeParam) {
        return toAjax(qqchComparisonSchemeService.updateQqchComparisonScheme(qqchComparisonSchemeParam));
    }

    @PreAuthorize(hasPermi = "qqchComparisonScheme:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchComparisonSchemeList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchComparisonScheme> qqchComparisonSchemeListParam) {
        return toAjax(qqchComparisonSchemeService.updateQqchComparisonSchemeList(qqchComparisonSchemeListParam));
    }

    @PreAuthorize(hasPermi = "qqchComparisonScheme:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchComparisonScheme(@Validated(ValidationGroups.Delete.class) @RequestBody QqchComparisonScheme qqchComparisonSchemeParam) {
        return toAjax(qqchComparisonSchemeService.deleteQqchComparisonScheme(qqchComparisonSchemeParam));
    }

    @PreAuthorize(hasPermi = "qqchComparisonScheme:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchComparisonSchemeByPks(@PathVariable Long[] ids) {
        List<Long> qqchComparisonSchemePkList = Arrays.asList(ids);
        return toAjax(qqchComparisonSchemeService.deleteQqchComparisonSchemeByPks(qqchComparisonSchemePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchComparisonScheme qqchComparisonSchemeParam) throws IOException {
        List<QqchComparisonScheme> qqchComparisonSchemeList = qqchComparisonSchemeService.getQqchComparisonSchemeList(qqchComparisonSchemeParam);
        ExcelUtils<QqchComparisonScheme> util = new ExcelUtils<>(QqchComparisonScheme.class);
        util.exportExcel(response, qqchComparisonSchemeList, DateUtils.getDate());
    }
}
