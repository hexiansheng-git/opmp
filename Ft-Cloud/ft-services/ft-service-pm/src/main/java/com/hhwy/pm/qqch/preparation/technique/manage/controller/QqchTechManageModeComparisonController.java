package com.hhwy.pm.qqch.preparation.technique.manage.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchTechManageModeComparison;
import com.hhwy.pm.qqch.preparation.technique.manage.service.IQqchTechManageModeComparisonService;
import com.hhwy.utils.validation.ValidationGroups;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-07-11 15:17:31
 * @remark 3.3.1技术管理模式比选
 */
@Validated
@RestController
@RequestMapping("/qqchTechManageModeComparison")
public class QqchTechManageModeComparisonController extends BaseController {

    @Autowired
    private IQqchTechManageModeComparisonService qqchTechManageModeComparisonService;

    @PreAuthorize(hasPermi = "qqchTechManageModeComparison:list")
    @GetMapping("/getList")
    public AjaxResult getList(
        @Validated(ValidationGroups.Select.class) @RequestBody QqchTechManageModeComparison qqchTechManageModeComparisonParam) {
        List<QqchTechManageModeComparison> qqchTechManageModeComparisonList = qqchTechManageModeComparisonService
            .getQqchTechManageModeComparisonList(qqchTechManageModeComparisonParam);
        return AjaxResult.success(qqchTechManageModeComparisonList);
    }

    @PreAuthorize(hasPermi = "qqchTechManageModeComparison:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody List<QqchTechManageModeComparison> qqchTechManageModeComparisonListParam) {
        qqchTechManageModeComparisonService.batchSave(qqchTechManageModeComparisonListParam);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchTechManageModeComparison:remove")
    @PostMapping("/remove")
    public AjaxResult deleteQqchTechManageModeComparisonByPks(Long[] ids) {
        List<Long> qqchTechManageModeComparisonPkList = Arrays.asList(ids);
        return toAjax(qqchTechManageModeComparisonService
            .deleteQqchTechManageModeComparisonByPks(qqchTechManageModeComparisonPkList));
    }
}
