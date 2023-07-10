package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeContent;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeContentService;
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
 * @date 2023-07-07 19:02:54
 * @remark 比选方案比选内容
 */
@Validated
@RestController
@RequestMapping("/qqchComparisonSchemeContent")
public class QqchComparisonSchemeContentController extends BaseController {

    @Autowired
    private IQqchComparisonSchemeContentService qqchComparisonSchemeContentService;


    @PreAuthorize(hasPermi = "qqchComparisonSchemeContent:list")
    @GetMapping
    public AjaxResult getQqchComparisonSchemeContent(@Validated(ValidationGroups.Get.class) @RequestBody QqchComparisonSchemeContent qqchComparisonSchemeContentParam) {
        QqchComparisonSchemeContent qqchComparisonSchemeContent = qqchComparisonSchemeContentService.getQqchComparisonSchemeContent(qqchComparisonSchemeContentParam);
        return AjaxResult.success(qqchComparisonSchemeContent);
    }

    @PreAuthorize(hasPermi = "qqchComparisonSchemeContent:list")
    @GetMapping("/list")
    public AjaxResult getQqchComparisonSchemeContentList(@Validated(ValidationGroups.Select.class) @RequestBody QqchComparisonSchemeContent qqchComparisonSchemeContentParam) {
        startPage();
        List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList = qqchComparisonSchemeContentService.getQqchComparisonSchemeContentList(qqchComparisonSchemeContentParam);
        return getDataTableAjaxResult(qqchComparisonSchemeContentList);
    }

    @PreAuthorize(hasPermi = "qqchComparisonSchemeContent:add")
    @PostMapping("/add")
    public AjaxResult insertQqchComparisonSchemeContent(@Validated(ValidationGroups.Save.class) @RequestBody QqchComparisonSchemeContent qqchComparisonSchemeContentParam) {
        qqchComparisonSchemeContentService.insertQqchComparisonSchemeContent(qqchComparisonSchemeContentParam);
        return AjaxResult.success(qqchComparisonSchemeContentParam);
    }

    @PreAuthorize(hasPermi = "qqchComparisonSchemeContent:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchComparisonSchemeContentList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchComparisonSchemeContent> qqchComparisonSchemeContentListParam) {
        qqchComparisonSchemeContentService.insertQqchComparisonSchemeContentList(qqchComparisonSchemeContentListParam);
        return AjaxResult.success(qqchComparisonSchemeContentListParam);
    }

    @PreAuthorize(hasPermi = "qqchComparisonSchemeContent:update")
    @PostMapping("/update")
    public AjaxResult updateQqchComparisonSchemeContent(@Validated(ValidationGroups.Update.class) @RequestBody QqchComparisonSchemeContent qqchComparisonSchemeContentParam) {
        return toAjax(qqchComparisonSchemeContentService.updateQqchComparisonSchemeContent(qqchComparisonSchemeContentParam));
    }

    @PreAuthorize(hasPermi = "qqchComparisonSchemeContent:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchComparisonSchemeContentList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchComparisonSchemeContent> qqchComparisonSchemeContentListParam) {
        return toAjax(qqchComparisonSchemeContentService.updateQqchComparisonSchemeContentList(qqchComparisonSchemeContentListParam));
    }

    @PreAuthorize(hasPermi = "qqchComparisonSchemeContent:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchComparisonSchemeContent(@Validated(ValidationGroups.Delete.class) @RequestBody QqchComparisonSchemeContent qqchComparisonSchemeContentParam) {
        return toAjax(qqchComparisonSchemeContentService.deleteQqchComparisonSchemeContent(qqchComparisonSchemeContentParam));
    }

    @PreAuthorize(hasPermi = "qqchComparisonSchemeContent:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchComparisonSchemeContentByPks(@PathVariable Long[] ids) {
        List<Long> qqchComparisonSchemeContentPkList = Arrays.asList(ids);
        return toAjax(qqchComparisonSchemeContentService.deleteQqchComparisonSchemeContentByPks(qqchComparisonSchemeContentPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchComparisonSchemeContent qqchComparisonSchemeContentParam) throws IOException {
        List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList = qqchComparisonSchemeContentService.getQqchComparisonSchemeContentList(qqchComparisonSchemeContentParam);
        ExcelUtils<QqchComparisonSchemeContent> util = new ExcelUtils<>(QqchComparisonSchemeContent.class);
        util.exportExcel(response, qqchComparisonSchemeContentList, DateUtils.getDate());
    }
}
