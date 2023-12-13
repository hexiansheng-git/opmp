package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchOptimizeProcedurePlanVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchOptimizeProcedurePlanService;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
//import com.hhwy.common.security.annotation.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-07-07 18:35:53
 * @remark 优化程序策划
 */
@Validated
@RestController
@RequestMapping("/qqchOptimizeProcedurePlan")
public class QqchOptimizeProcedurePlanController extends BaseController {

    @Autowired
    private IQqchOptimizeProcedurePlanService qqchOptimizeProcedurePlanService;


    /**
     * 优化程序策划台账
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getQqchOptimizeProcedurePlanVo(BigDecimal version) {
        QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo = qqchOptimizeProcedurePlanService.getQqchOptimizeProcedurePlanVo(version);
        return AjaxResult.success(qqchOptimizeProcedurePlanVo);
    }

    /**
     * 保存
     * @param qqchOptimizeProcedurePlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchOptimizeProcedurePlan:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计优化变更策划", name = "\n" +
            "2.5.2 优化程序策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo) {
        qqchOptimizeProcedurePlanService.save(qqchOptimizeProcedurePlanVo);
        return AjaxResult.success("保存成功！");
    }

    /**
     * 确认
     * @param qqchOptimizeProcedurePlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchOptimizeProcedurePlan:save")
    @PostMapping("/confirm")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-2.5 勘察设计优化变更策划", name = "\n" +
            "2.5.2 优化程序策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo) {
        qqchOptimizeProcedurePlanService.confirm(qqchOptimizeProcedurePlanVo);
        return AjaxResult.success("确认成功！");
    }
}
