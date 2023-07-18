package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchComparisonSchemeVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author han
 * @date 2023-07-07 18:35:38
 * @remark 重大设计方案比选-方案
 */
@Validated
@RestController
@RequestMapping("/qqchComparisonScheme")
public class QqchComparisonSchemeController extends BaseController {

    @Autowired
    private IQqchComparisonSchemeService qqchComparisonSchemeService;


    /**
     * 重大设计方案比选-方案台账
     * @return
     */
    @PreAuthorize(hasPermi = "qqchComparisonScheme:list")
    @GetMapping("/list")
    public AjaxResult getQqchComparisonSchemeVo() {
        QqchComparisonSchemeVo qqchComparisonSchemeVo = qqchComparisonSchemeService.getQqchComparisonSchemeVo();
        return AjaxResult.success(qqchComparisonSchemeVo);
    }

    /**
     * 保存
     * @param qqchComparisonSchemeVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchComparisonScheme:update")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Update.class) @RequestBody QqchComparisonSchemeVo qqchComparisonSchemeVo) {
        qqchComparisonSchemeService.save(qqchComparisonSchemeVo);
        return AjaxResult.success(qqchComparisonSchemeVo);
    }

    /**
     * 确认
     * @param qqchComparisonSchemeVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchComparisonScheme:update")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchComparisonSchemeVo qqchComparisonSchemeVo) {
        qqchComparisonSchemeService.confirm(qqchComparisonSchemeVo);
        return AjaxResult.success(qqchComparisonSchemeVo);
    }
}
