package com.hhwy.pm.qqch.preparation.technique.scheme.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchMajorConstructionComparisonVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchMajorConstructionComparisonService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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

    @GetMapping("/getTreeList")
    public AjaxResult getTreeList(BigDecimal version) {
        QqchMajorConstructionComparisonVo qqchMajorConstructionComparisonVo = qqchMajorConstructionComparisonService
            .getQqchMajorConstructionComparisonList(version);
        return AjaxResult.success(qqchMajorConstructionComparisonVo);
    }

    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchMajorConstructionComparisonVo qqchMajorConstructionComparisonVo) {
        qqchMajorConstructionComparisonService.batchSave(qqchMajorConstructionComparisonVo);
        return AjaxResult.success();
    }
}
