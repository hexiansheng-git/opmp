package com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.domain.QqchTechRiskSolutions;
import com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.service.IQqchTechRiskSolutionsService;
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
 * @date 2023-07-25 10:55:16
 * @remark 技术风险及应对措施
 */
@Validated
@RestController
@RequestMapping("/qqchTechRiskSolutions")
public class QqchTechRiskSolutionsController extends BaseController {

    @Autowired
    private IQqchTechRiskSolutionsService qqchTechRiskSolutionsService;


    @PreAuthorize(hasPermi = "qqchTechRiskSolutions:list")
    @GetMapping
    public AjaxResult getQqchTechRiskSolutions(@Validated(ValidationGroups.Get.class) QqchTechRiskSolutions qqchTechRiskSolutionsParam) {
        QqchTechRiskSolutions qqchTechRiskSolutions = qqchTechRiskSolutionsService.getQqchTechRiskSolutions(qqchTechRiskSolutionsParam);
        return AjaxResult.success(qqchTechRiskSolutions);
    }

    @PreAuthorize(hasPermi = "qqchTechRiskSolutions:list")
    @GetMapping("/list")
    public AjaxResult getQqchTechRiskSolutionsList(@Validated(ValidationGroups.Select.class) QqchTechRiskSolutions qqchTechRiskSolutionsParam) {
        startPage();
        List<QqchTechRiskSolutions> qqchTechRiskSolutionsList = qqchTechRiskSolutionsService.getQqchTechRiskSolutionsList(qqchTechRiskSolutionsParam);
        return getDataTableAjaxResult(qqchTechRiskSolutionsList);
    }

    @PreAuthorize(hasPermi = "qqchTechRiskSolutions:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTechRiskSolutions(@Validated(ValidationGroups.Save.class) @RequestBody QqchTechRiskSolutions qqchTechRiskSolutionsParam) {
        qqchTechRiskSolutionsService.insertQqchTechRiskSolutions(qqchTechRiskSolutionsParam);
        return AjaxResult.success(qqchTechRiskSolutionsParam);
    }

    @PreAuthorize(hasPermi = "qqchTechRiskSolutions:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTechRiskSolutionsList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTechRiskSolutions> qqchTechRiskSolutionsListParam) {
        qqchTechRiskSolutionsService.insertQqchTechRiskSolutionsList(qqchTechRiskSolutionsListParam);
        return AjaxResult.success(qqchTechRiskSolutionsListParam);
    }

    @PreAuthorize(hasPermi = "qqchTechRiskSolutions:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTechRiskSolutions(@Validated(ValidationGroups.Update.class) @RequestBody QqchTechRiskSolutions qqchTechRiskSolutionsParam) {
        return toAjax(qqchTechRiskSolutionsService.updateQqchTechRiskSolutions(qqchTechRiskSolutionsParam));
    }

    @PreAuthorize(hasPermi = "qqchTechRiskSolutions:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTechRiskSolutionsList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTechRiskSolutions> qqchTechRiskSolutionsListParam) {
        return toAjax(qqchTechRiskSolutionsService.updateQqchTechRiskSolutionsList(qqchTechRiskSolutionsListParam));
    }

    @PreAuthorize(hasPermi = "qqchTechRiskSolutions:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTechRiskSolutions(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTechRiskSolutions qqchTechRiskSolutionsParam) {
        return toAjax(qqchTechRiskSolutionsService.deleteQqchTechRiskSolutions(qqchTechRiskSolutionsParam));
    }

    @PreAuthorize(hasPermi = "qqchTechRiskSolutions:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTechRiskSolutionsByPks(@PathVariable Long[] ids) {
        List<Long> qqchTechRiskSolutionsPkList = Arrays.asList(ids);
        return toAjax(qqchTechRiskSolutionsService.deleteQqchTechRiskSolutionsByPks(qqchTechRiskSolutionsPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTechRiskSolutions qqchTechRiskSolutionsParam) throws IOException {
        List<QqchTechRiskSolutions> qqchTechRiskSolutionsList = qqchTechRiskSolutionsService.getQqchTechRiskSolutionsList(qqchTechRiskSolutionsParam);
        ExcelUtils<QqchTechRiskSolutions> util = new ExcelUtils<>(QqchTechRiskSolutions.class);
        util.exportExcel(response, qqchTechRiskSolutionsList, DateUtils.getDate());
    }
}
