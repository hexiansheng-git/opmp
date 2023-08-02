package com.hhwy.pm.qqch.preparation.costControl.masterContract.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchSpecialCondition;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.service.IQqchSpecialConditionService;
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
 * @date 2023-08-02 11:39:48
 * @remark 专用条件梳理
 */
@Validated
@RestController
@RequestMapping("/qqchSpecialCondition")
public class QqchSpecialConditionController extends BaseController {

    @Autowired
    private IQqchSpecialConditionService qqchSpecialConditionService;


    @PreAuthorize(hasPermi = "qqchSpecialCondition:list")
    @GetMapping
    public AjaxResult getQqchSpecialCondition(@Validated(ValidationGroups.Get.class) QqchSpecialCondition qqchSpecialConditionParam) {
        QqchSpecialCondition qqchSpecialCondition = qqchSpecialConditionService.getQqchSpecialCondition(qqchSpecialConditionParam);
        return AjaxResult.success(qqchSpecialCondition);
    }

    @PreAuthorize(hasPermi = "qqchSpecialCondition:list")
    @GetMapping("/list")
    public AjaxResult getQqchSpecialConditionList(@Validated(ValidationGroups.Select.class) QqchSpecialCondition qqchSpecialConditionParam) {
        startPage();
        List<QqchSpecialCondition> qqchSpecialConditionList = qqchSpecialConditionService.getQqchSpecialConditionList(qqchSpecialConditionParam);
        return getDataTableAjaxResult(qqchSpecialConditionList);
    }

    @PreAuthorize(hasPermi = "qqchSpecialCondition:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSpecialCondition(@Validated(ValidationGroups.Save.class) @RequestBody QqchSpecialCondition qqchSpecialConditionParam) {
        qqchSpecialConditionService.insertQqchSpecialCondition(qqchSpecialConditionParam);
        return AjaxResult.success(qqchSpecialConditionParam);
    }

    @PreAuthorize(hasPermi = "qqchSpecialCondition:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSpecialConditionList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSpecialCondition> qqchSpecialConditionListParam) {
        qqchSpecialConditionService.insertQqchSpecialConditionList(qqchSpecialConditionListParam);
        return AjaxResult.success(qqchSpecialConditionListParam);
    }

    @PreAuthorize(hasPermi = "qqchSpecialCondition:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSpecialCondition(@Validated(ValidationGroups.Update.class) @RequestBody QqchSpecialCondition qqchSpecialConditionParam) {
        return toAjax(qqchSpecialConditionService.updateQqchSpecialCondition(qqchSpecialConditionParam));
    }

    @PreAuthorize(hasPermi = "qqchSpecialCondition:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSpecialConditionList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSpecialCondition> qqchSpecialConditionListParam) {
        return toAjax(qqchSpecialConditionService.updateQqchSpecialConditionList(qqchSpecialConditionListParam));
    }

    @PreAuthorize(hasPermi = "qqchSpecialCondition:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSpecialCondition(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSpecialCondition qqchSpecialConditionParam) {
        return toAjax(qqchSpecialConditionService.deleteQqchSpecialCondition(qqchSpecialConditionParam));
    }

    @PreAuthorize(hasPermi = "qqchSpecialCondition:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSpecialConditionByPks(@PathVariable Long[] ids) {
        List<Long> qqchSpecialConditionPkList = Arrays.asList(ids);
        return toAjax(qqchSpecialConditionService.deleteQqchSpecialConditionByPks(qqchSpecialConditionPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSpecialCondition qqchSpecialConditionParam) throws IOException {
        List<QqchSpecialCondition> qqchSpecialConditionList = qqchSpecialConditionService.getQqchSpecialConditionList(qqchSpecialConditionParam);
        ExcelUtils<QqchSpecialCondition> util = new ExcelUtils<>(QqchSpecialCondition.class);
        util.exportExcel(response, qqchSpecialConditionList, DateUtils.getDate());
    }
}
