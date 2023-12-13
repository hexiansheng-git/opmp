package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchChangeProcedurePlanVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchChangeProcedurePlanService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
//    @PreAuthorize(hasPermi = "qqchChangeProcedurePlan:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计优化变更策划", name = "\n" +
            "2.5.1 优化变更组织策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult editQqchChangeProcedurePlanList(@Validated(ValidationGroups.Update.class) @RequestBody QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo) {
        qqchChangeProcedurePlanService.save(qqchChangeProcedurePlanVo);
        return AjaxResult.success();
    }

    /**
     * 确认
     * @param qqchChangeProcedurePlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchChangeProcedurePlan:save")
    @PostMapping("/confirm")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-2.5 勘察设计优化变更策划", name = "\n" +
            "2.5.1 优化变更组织策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo) {
        qqchChangeProcedurePlanService.confirm(qqchChangeProcedurePlanVo);
        return AjaxResult.success("确认成功！");
    }
}
