package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchOptimizeChangeOrganizationVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchOptimizeChangeOrganizationService;
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
 * @date 2023-07-07 18:35:50
 * @remark 优化变更组织策划
 */
@Validated
@RestController
@RequestMapping("/qqchOptimizeChangeOrganization")
public class QqchOptimizeChangeOrganizationController extends BaseController {

    @Autowired
    private IQqchOptimizeChangeOrganizationService qqchOptimizeChangeOrganizationService;


    /**
     * 优化变更组织策划台账
     * @return
     */
    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:list")
    @GetMapping("/treeList")
    public AjaxResult getQqchOptimizeChangeOrganizationVo(BigDecimal version) {
        QqchOptimizeChangeOrganizationVo qqchOptimizeChangeOrganizationVo = qqchOptimizeChangeOrganizationService.getQqchOptimizeChangeOrganizationVo(version);
        return AjaxResult.success(qqchOptimizeChangeOrganizationVo);
    }

    /**
     * 保存
     * @param qqchOptimizeChangeOrganizationVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:update")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Update.class) @RequestBody QqchOptimizeChangeOrganizationVo qqchOptimizeChangeOrganizationVo) {
        qqchOptimizeChangeOrganizationService.save(qqchOptimizeChangeOrganizationVo);
        return AjaxResult.success();
    }

    /**
     * 确认/提交
     * @param qqchOptimizeChangeOrganizationVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:update")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchOptimizeChangeOrganizationVo qqchOptimizeChangeOrganizationVo){
        qqchOptimizeChangeOrganizationService.confirm(qqchOptimizeChangeOrganizationVo);
        return AjaxResult.success("确认成功！");
    }
}
