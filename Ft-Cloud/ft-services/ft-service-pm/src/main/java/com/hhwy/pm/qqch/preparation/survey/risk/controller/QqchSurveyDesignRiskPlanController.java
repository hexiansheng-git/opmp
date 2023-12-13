package com.hhwy.pm.qqch.preparation.survey.risk.controller;

import com.hhwy.pm.qqch.preparation.survey.risk.domain.vo.QqchSurveyDesignRiskPlanVo;
import com.hhwy.pm.qqch.preparation.survey.risk.service.IQqchSurveyDesignRiskPlanService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
//import com.hhwy.common.security.annotation.PreAuthorize;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-07-13 11:39:34
 * @remark 勘察设计风险策划
 */
@Validated
@RestController
@RequestMapping("/qqchSurveyDesignRiskPlan")
public class QqchSurveyDesignRiskPlanController extends BaseController {

    @Autowired
    private IQqchSurveyDesignRiskPlanService qqchSurveyDesignRiskPlanService;


    /**
     * 勘察设计风险策划Vo
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getQqchSurveyDesignRiskPlanVo(BigDecimal version) {
        QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo = qqchSurveyDesignRiskPlanService.getQqchSurveyDesignRiskPlanVo(version);
        return AjaxResult.success(qqchSurveyDesignRiskPlanVo);
    }

    /**
     * 保存
     * @param qqchSurveyDesignRiskPlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计优化变更策划", name = "\n" +
            "2.7.1 勘察设计风险策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo) {
        qqchSurveyDesignRiskPlanService.save(qqchSurveyDesignRiskPlanVo);
        return AjaxResult.success(qqchSurveyDesignRiskPlanVo);
    }

    /**
     * 确认
     * @param qqchSurveyDesignRiskPlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyDesignRiskPlan:save")
    @PostMapping("/confirm")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-2.7 勘察设计风险管控措施", name = "\n" +
            "2.7.1 勘察设计风险策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo) {
        qqchSurveyDesignRiskPlanService.confirm(qqchSurveyDesignRiskPlanVo);
        return AjaxResult.success(qqchSurveyDesignRiskPlanVo);
    }
}
