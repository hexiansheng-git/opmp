package com.hhwy.pm.qqch.preparation.quality.qc.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.vo.QqchQcImplementPlanVo;
import com.hhwy.pm.qqch.preparation.quality.qc.service.IQqchQcImplementPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-08-04 10:30:39
 * @remark 9.6.2 QC实施计划
 */
@Validated
@RestController
@RequestMapping("/qqchQcImplementPlan")
public class QqchQcImplementPlanController extends BaseController {

    @Autowired
    private IQqchQcImplementPlanService qqchQcImplementPlanService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchQcImplementPlan:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchQcImplementPlanVo qqchQcImplementPlanVo = qqchQcImplementPlanService.getQqchQcImplementPlanList(version);
        return AjaxResult.success(qqchQcImplementPlanVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchQcImplementPlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchQcImplementPlan:update")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Update.class) @RequestBody QqchQcImplementPlanVo qqchQcImplementPlanVo) {
        qqchQcImplementPlanService.updateQqchQcImplementPlan(qqchQcImplementPlanVo);
        return AjaxResult.success();
    }
}
