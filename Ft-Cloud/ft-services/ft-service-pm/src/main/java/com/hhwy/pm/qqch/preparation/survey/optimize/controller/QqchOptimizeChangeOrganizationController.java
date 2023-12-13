package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchOptimizeChangeOrganizationVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchOptimizeChangeOrganizationService;
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
//    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:save")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计优化变更策划", name = "\n" +
            "2.5.1 优化变更组织策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Update.class) @RequestBody QqchOptimizeChangeOrganizationVo qqchOptimizeChangeOrganizationVo) {
        qqchOptimizeChangeOrganizationService.save(qqchOptimizeChangeOrganizationVo);
        return AjaxResult.success();
    }

    /**
     * 确认/提交
     * @param qqchOptimizeChangeOrganizationVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:save")
    @PostMapping("/confirm")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-2.5 勘察设计优化变更策划", name = "\n" +
            "2.5.1 优化变更组织策划" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchOptimizeChangeOrganizationVo qqchOptimizeChangeOrganizationVo){
        qqchOptimizeChangeOrganizationService.confirm(qqchOptimizeChangeOrganizationVo);
        return AjaxResult.success("确认成功！");
    }
}
