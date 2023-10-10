package com.hhwy.pm.qqch.preparation.costControl.other.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.costControl.other.domain.vo.QqchOtherCostControlMeasuresVo;
import com.hhwy.pm.qqch.preparation.costControl.other.service.IQqchOtherCostControlMeasuresService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-11 09:38:56
 * @remark 5.5 其他成本管控工作安排及措施
 */
@Validated
@RestController
@RequestMapping("/qqchOtherCostControlMeasures")
public class QqchOtherCostControlMeasuresController extends BaseController {

    @Autowired
    private IQqchOtherCostControlMeasuresService qqchOtherCostControlMeasuresService;

    /**
     * 树列表
     *
     * @param version
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchOtherCostControlMeasures:list")
    @GetMapping("/getTreeList")
    public AjaxResult getTreeList(BigDecimal version) {
        QqchOtherCostControlMeasuresVo qqchOtherCostControlMeasuresVo = qqchOtherCostControlMeasuresService
            .getQqchOtherCostControlMeasuresList(version);
        return AjaxResult.success(qqchOtherCostControlMeasuresVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchOtherCostControlMeasuresVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchOtherCostControlMeasures:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchOtherCostControlMeasuresVo qqchOtherCostControlMeasuresVo) {
        qqchOtherCostControlMeasuresService.batchSave(qqchOtherCostControlMeasuresVo);
        return AjaxResult.success();
    }
}
