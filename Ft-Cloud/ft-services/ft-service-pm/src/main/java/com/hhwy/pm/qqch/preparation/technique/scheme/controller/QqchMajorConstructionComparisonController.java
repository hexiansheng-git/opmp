package com.hhwy.pm.qqch.preparation.technique.scheme.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchMajorConstructionComparison;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchMajorConstructionComparisonVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchMajorConstructionComparisonService;
import com.hhwy.utils.validation.ValidationGroups;
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
        QqchMajorConstructionComparisonVo qqchMajorConstructionComparisonVo = qqchMajorConstructionComparisonService
            .getQqchMajorConstructionComparisonList(qqchMajorConstructionComparisonParam);
        return AjaxResult.success(qqchMajorConstructionComparisonVo);
    }

    @PreAuthorize(hasPermi = "qqchMajorConstructionComparison:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchMajorConstructionComparisonVo qqchMajorConstructionComparisonVo) {
        qqchMajorConstructionComparisonService.batchSave(qqchMajorConstructionComparisonVo);
        return AjaxResult.success();
    }
}
