package com.hhwy.pm.qqch.preparation.technique.scheme.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchMajorConstructionComparison;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchMajorConstructionComparisonService;
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
 * @date 2023-07-13 14:27:03
 * @remark 3.4.1重大施工方案比选
 */
@Validated
@RestController
@RequestMapping("/qqchMajorConstructionComparison")
public class QqchMajorConstructionComparisonController extends BaseController {

    @Autowired
    private IQqchMajorConstructionComparisonService qqchMajorConstructionComparisonService;

    @PreAuthorize(hasPermi = "qqchMajorConstructionComparison:list")
    @GetMapping("/getTreeList")
    public AjaxResult getTreeList(
        @Validated(ValidationGroups.Select.class) @RequestBody QqchMajorConstructionComparison qqchMajorConstructionComparisonParam) {
        List<QqchMajorConstructionComparison> qqchMajorConstructionComparisonList = qqchMajorConstructionComparisonService
            .getQqchMajorConstructionComparisonList(qqchMajorConstructionComparisonParam);
        return AjaxResult.success(qqchMajorConstructionComparisonList);
    }

    @PreAuthorize(hasPermi = "qqchMajorConstructionComparison:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody List<QqchMajorConstructionComparison> qqchMajorConstructionComparisonListParam) {
        qqchMajorConstructionComparisonService.batchSave(qqchMajorConstructionComparisonListParam);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchMajorConstructionComparison:remove")
    @PostMapping("/remove")
    public AjaxResult deleteQqchMajorConstructionComparisonByPks(Long[] ids) {
        List<Long> qqchMajorConstructionComparisonPkList = Arrays.asList(ids);
        return toAjax(qqchMajorConstructionComparisonService
            .deleteQqchMajorConstructionComparisonByPks(qqchMajorConstructionComparisonPkList));
    }
}
