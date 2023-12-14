package com.hhwy.pm.qqch.preparation.quality.qc.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.vo.QqchQcImplementPlanVo;
import com.hhwy.pm.qqch.preparation.quality.qc.service.IQqchQcImplementPlanService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
    @PreAuthorize(hasPermi = "qqchQcImplementPlan:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-质量策划-9.6 QC活动", name = "\n" +
            "9.6.2 QC实施计划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(@RequestBody QqchQcImplementPlanVo qqchQcImplementPlanVo) {
        qqchQcImplementPlanService.batchSave(qqchQcImplementPlanVo);
        return AjaxResult.success();
    }
}
