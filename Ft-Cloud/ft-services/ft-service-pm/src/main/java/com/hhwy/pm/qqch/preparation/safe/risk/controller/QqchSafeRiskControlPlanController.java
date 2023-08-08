package com.hhwy.pm.qqch.preparation.safe.risk.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.QqchSafeRiskControlPlanVo;
import com.hhwy.pm.qqch.preparation.safe.risk.service.IQqchSafeRiskControlPlanService;
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
 * @date 2023-08-07 13:53:33
 * @remark 8.2.3 安全风险过程管控策划
 */
@Validated
@RestController
@RequestMapping("/qqchSafeRiskControlPlan")
public class QqchSafeRiskControlPlanController extends BaseController {

    @Autowired
    private IQqchSafeRiskControlPlanService qqchSafeRiskControlPlanService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSafeRiskControlPlan:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchSafeRiskControlPlanVo qqchSafeRiskControlPlanVo = qqchSafeRiskControlPlanService
            .getQqchSafeRiskControlPlanList(version);
        return AjaxResult.success(qqchSafeRiskControlPlanVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchSafeRiskControlPlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSafeRiskControlPlan:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchSafeRiskControlPlanVo qqchSafeRiskControlPlanVo) {
        qqchSafeRiskControlPlanService.batchSave(qqchSafeRiskControlPlanVo);
        return AjaxResult.success();
    }
}
