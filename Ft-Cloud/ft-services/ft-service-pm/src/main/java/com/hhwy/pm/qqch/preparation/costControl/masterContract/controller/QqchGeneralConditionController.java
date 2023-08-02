package com.hhwy.pm.qqch.preparation.costControl.masterContract.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchGeneralCondition;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.service.IQqchGeneralConditionService;
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
 * @date 2023-08-02 11:39:46
 * @remark 通用条件梳理
 */
@Validated
@RestController
@RequestMapping("/qqchGeneralCondition")
public class QqchGeneralConditionController extends BaseController {

    @Autowired
    private IQqchGeneralConditionService qqchGeneralConditionService;


    @PreAuthorize(hasPermi = "qqchGeneralCondition:list")
    @GetMapping
    public AjaxResult getQqchGeneralCondition(@Validated(ValidationGroups.Get.class) QqchGeneralCondition qqchGeneralConditionParam) {
        QqchGeneralCondition qqchGeneralCondition = qqchGeneralConditionService.getQqchGeneralCondition(qqchGeneralConditionParam);
        return AjaxResult.success(qqchGeneralCondition);
    }

    @PreAuthorize(hasPermi = "qqchGeneralCondition:list")
    @GetMapping("/list")
    public AjaxResult getQqchGeneralConditionList(@Validated(ValidationGroups.Select.class) QqchGeneralCondition qqchGeneralConditionParam) {
        startPage();
        List<QqchGeneralCondition> qqchGeneralConditionList = qqchGeneralConditionService.getQqchGeneralConditionList(qqchGeneralConditionParam);
        return getDataTableAjaxResult(qqchGeneralConditionList);
    }

    @PreAuthorize(hasPermi = "qqchGeneralCondition:add")
    @PostMapping("/add")
    public AjaxResult insertQqchGeneralCondition(@Validated(ValidationGroups.Save.class) @RequestBody QqchGeneralCondition qqchGeneralConditionParam) {
        qqchGeneralConditionService.insertQqchGeneralCondition(qqchGeneralConditionParam);
        return AjaxResult.success(qqchGeneralConditionParam);
    }

    @PreAuthorize(hasPermi = "qqchGeneralCondition:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchGeneralConditionList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchGeneralCondition> qqchGeneralConditionListParam) {
        qqchGeneralConditionService.insertQqchGeneralConditionList(qqchGeneralConditionListParam);
        return AjaxResult.success(qqchGeneralConditionListParam);
    }

    @PreAuthorize(hasPermi = "qqchGeneralCondition:update")
    @PostMapping("/update")
    public AjaxResult updateQqchGeneralCondition(@Validated(ValidationGroups.Update.class) @RequestBody QqchGeneralCondition qqchGeneralConditionParam) {
        return toAjax(qqchGeneralConditionService.updateQqchGeneralCondition(qqchGeneralConditionParam));
    }

    @PreAuthorize(hasPermi = "qqchGeneralCondition:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchGeneralConditionList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchGeneralCondition> qqchGeneralConditionListParam) {
        return toAjax(qqchGeneralConditionService.updateQqchGeneralConditionList(qqchGeneralConditionListParam));
    }

    @PreAuthorize(hasPermi = "qqchGeneralCondition:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchGeneralCondition(@Validated(ValidationGroups.Delete.class) @RequestBody QqchGeneralCondition qqchGeneralConditionParam) {
        return toAjax(qqchGeneralConditionService.deleteQqchGeneralCondition(qqchGeneralConditionParam));
    }

    @PreAuthorize(hasPermi = "qqchGeneralCondition:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchGeneralConditionByPks(@PathVariable Long[] ids) {
        List<Long> qqchGeneralConditionPkList = Arrays.asList(ids);
        return toAjax(qqchGeneralConditionService.deleteQqchGeneralConditionByPks(qqchGeneralConditionPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchGeneralCondition qqchGeneralConditionParam) throws IOException {
        List<QqchGeneralCondition> qqchGeneralConditionList = qqchGeneralConditionService.getQqchGeneralConditionList(qqchGeneralConditionParam);
        ExcelUtils<QqchGeneralCondition> util = new ExcelUtils<>(QqchGeneralCondition.class);
        util.exportExcel(response, qqchGeneralConditionList, DateUtils.getDate());
    }
}
