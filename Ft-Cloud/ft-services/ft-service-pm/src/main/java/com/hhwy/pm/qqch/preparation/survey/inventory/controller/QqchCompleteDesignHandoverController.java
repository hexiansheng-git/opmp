package com.hhwy.pm.qqch.preparation.survey.inventory.controller;

import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchCompleteDesignHandover;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo.QqchCompleteDesignHandoverVo;
import com.hhwy.pm.qqch.preparation.survey.inventory.service.IQqchCompleteDesignHandoverService;
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
 * @date 2023-07-11 17:11:14
 * @remark 完整设计交接情况
 */
@Validated
@RestController
@RequestMapping("/qqchCompleteDesignHandover")
public class QqchCompleteDesignHandoverController extends BaseController {

    @Autowired
    private IQqchCompleteDesignHandoverService qqchCompleteDesignHandoverService;


    /**
     * 完整设计交接情况台账
     * @return
     */
    @PreAuthorize(hasPermi = "qqchCompleteDesignHandover:list")
    @GetMapping("/list")
    public AjaxResult getQqchCompleteDesignHandoverList(BigDecimal version) {
        QqchCompleteDesignHandoverVo qqchCompleteDesignHandoverVo = qqchCompleteDesignHandoverService.getQqchCompleteDesignHandoverVo(version);
        return AjaxResult.success(qqchCompleteDesignHandoverVo);
    }

    /**
     * 保存
     * @param qqchCompleteDesignHandoverVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchCompleteDesignHandover:update")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Update.class) @RequestBody QqchCompleteDesignHandoverVo qqchCompleteDesignHandoverVo) {
        qqchCompleteDesignHandoverService.save(qqchCompleteDesignHandoverVo);
        return AjaxResult.success(qqchCompleteDesignHandoverVo);
    }

    /**
     * 确认
     * @param qqchCompleteDesignHandoverVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchCompleteDesignHandover:update")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchCompleteDesignHandoverVo qqchCompleteDesignHandoverVo) {
        qqchCompleteDesignHandoverService.confirm(qqchCompleteDesignHandoverVo);
        return AjaxResult.success(qqchCompleteDesignHandoverVo);
    }
}
