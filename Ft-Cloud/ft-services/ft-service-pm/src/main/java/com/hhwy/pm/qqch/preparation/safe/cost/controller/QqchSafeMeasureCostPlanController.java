package com.hhwy.pm.qqch.preparation.safe.cost.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.cost.domain.vo.QqchSafeMeasureCostPlanVo;
import com.hhwy.pm.qqch.preparation.safe.cost.service.IQqchSafeMeasureCostPlanService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-10 16:38:50
 * @remark 8.11 安全文明措施费策划
 */
@Validated
@RestController
@RequestMapping("/qqchSafeMeasureCostPlan")
public class QqchSafeMeasureCostPlanController extends BaseController {

    @Autowired
    private IQqchSafeMeasureCostPlanService qqchSafeMeasureCostPlanService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSafeMeasureCostPlan:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchSafeMeasureCostPlanVo qqchSafeMeasureCostPlanVo = qqchSafeMeasureCostPlanService
            .getQqchSafeMeasureCostPlanList(version);
        return AjaxResult.success(qqchSafeMeasureCostPlanVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchSafeMeasureCostPlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSafeMeasureCostPlan:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-8.11 安全文明措施费策划", name = "\n" +
            "8.11 安全文明措施费策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(@RequestBody QqchSafeMeasureCostPlanVo qqchSafeMeasureCostPlanVo) {
        qqchSafeMeasureCostPlanService.batchSave(qqchSafeMeasureCostPlanVo);
        return AjaxResult.success();
    }
}
