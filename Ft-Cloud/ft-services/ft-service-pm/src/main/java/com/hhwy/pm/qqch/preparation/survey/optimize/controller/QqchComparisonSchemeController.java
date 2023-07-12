package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonScheme;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
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


    /**
     * 重大设计方案比选-方案台账
     * @return
     */
    @PreAuthorize(hasPermi = "qqchComparisonScheme:list")
    @GetMapping("/list")
    public AjaxResult getQqchComparisonSchemeList() {
        List<QqchComparisonScheme> qqchComparisonSchemeList = qqchComparisonSchemeService.getQqchComparisonSchemeList();
        return AjaxResult.success(qqchComparisonSchemeList);
    }

    /**
     * 批量编辑
     * @param qqchComparisonSchemeListParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchComparisonScheme:update")
    @PostMapping("/batchEdit")
    public AjaxResult editQqchComparisonSchemeList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchComparisonScheme> qqchComparisonSchemeListParam) {
        return toAjax(qqchComparisonSchemeService.editQqchComparisonSchemeList(qqchComparisonSchemeListParam));
    }

    /**
     * 删除方案
     * @param schemeId 方案id
     * @return
     */
    @PreAuthorize(hasPermi = "qqchComparisonScheme:remove")
    @PostMapping("/remove/{schemeId}")
    @Validated(ValidationGroups.Get.class)
    public AjaxResult deleteQqchComparisonSchemeById(@NotNull(message = "方案id不能为空！",groups = ValidationGroups.Get.class) @PathVariable Long schemeId) {
        return toAjax(qqchComparisonSchemeService.deleteQqchComparisonSchemeById(schemeId));
    }

    /**
     * 删除行（多行）
     * @param schemeId
     * @return
     */
    @PreAuthorize(hasPermi = "qqchComparisonScheme:remove")
    @PostMapping("/removeLine/{schemeId}/{sorts}")
    @Validated(ValidationGroups.Delete.class)
    public AjaxResult deleteLine(@NotNull(message = "方案id不能为空！",groups = ValidationGroups.Get.class) @PathVariable("schemeId") Long schemeId,
                                 @NotNull(message = "序号不能为空！",groups = ValidationGroups.Get.class) @PathVariable("sorts") String[] sorts) {
        return toAjax(qqchComparisonSchemeService.deleteLine(schemeId,sorts));
    }

    /**
     * 删除列（多列）
     * @param headerIds
     * @return
     */
    @PreAuthorize(hasPermi = "qqchComparisonScheme:remove")
    @PostMapping("/removeColumn/{headerIds}")
    @Validated(ValidationGroups.Get.class)
    public AjaxResult deleteColumn(@NotNull(message = "表头id不能为空！",groups = ValidationGroups.Get.class) @PathVariable Long[] headerIds) {
        return toAjax(qqchComparisonSchemeService.deleteColumn(headerIds));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchComparisonScheme qqchComparisonSchemeParam) throws IOException {
        List<QqchComparisonScheme> qqchComparisonSchemeList = qqchComparisonSchemeService.getQqchComparisonSchemeList();
        ExcelUtils<QqchComparisonScheme> util = new ExcelUtils<>(QqchComparisonScheme.class);
        util.exportExcel(response, qqchComparisonSchemeList, DateUtils.getDate());
    }
}
