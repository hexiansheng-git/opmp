package com.hhwy.pm.qqch.preparation.safe.risk.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.vo.QqchSafeRiskControlPlanVo;
import com.hhwy.pm.qqch.preparation.safe.risk.service.IQqchSafeRiskControlPlanService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
//    @PreAuthorize(hasPermi = "qqchSafeRiskControlPlan:list")
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
//    @PreAuthorize(hasPermi = "qqchSafeRiskControlPlan:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-8.2 安全风险管控策划", name = "\n" +
            "8.2.3 安全风险过程管控策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(@RequestBody QqchSafeRiskControlPlanVo qqchSafeRiskControlPlanVo) {
        qqchSafeRiskControlPlanService.batchSave(qqchSafeRiskControlPlanVo);
        return AjaxResult.success();
    }
}
