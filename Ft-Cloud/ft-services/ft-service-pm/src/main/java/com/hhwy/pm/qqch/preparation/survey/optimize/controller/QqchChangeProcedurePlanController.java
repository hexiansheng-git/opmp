package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchChangeProcedurePlanVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchChangeProcedurePlanService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-07-07 18:35:34
 * @remark 变更程序策划
 */
@Validated
@RestController
@RequestMapping("/qqchChangeProcedurePlan")
public class QqchChangeProcedurePlanController extends BaseController {

    @Autowired
    private IQqchChangeProcedurePlanService qqchChangeProcedurePlanService;


    /**
     * 变更程序策划台账
     *
     * @return
     */
    @PreAuthorize(hasPermi = "qqchChangeProcedurePlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchChangeProcedurePlanVo(BigDecimal version) {
        QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo = qqchChangeProcedurePlanService.getQqchChangeProcedurePlanVo(version);
        return AjaxResult.success(qqchChangeProcedurePlanVo);
    }

    /**
     * 保存
     * @param qqchChangeProcedurePlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchChangeProcedurePlan:update")
    @PostMapping("/save")
    public AjaxResult editQqchChangeProcedurePlanList(@Validated(ValidationGroups.Update.class) @RequestBody QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo) {
        qqchChangeProcedurePlanService.save(qqchChangeProcedurePlanVo);
        return AjaxResult.success();
    }

    /**
     * 确认
     * @param qqchChangeProcedurePlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchChangeProcedurePlan:update")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo) {
        qqchChangeProcedurePlanService.confirm(qqchChangeProcedurePlanVo);
        return AjaxResult.success("确认成功！");
    }
}
