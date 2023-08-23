package com.hhwy.pm.qqch.preparation.survey.inventory.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo.QqchCompleteDesignHandoverVo;
import com.hhwy.pm.qqch.preparation.survey.inventory.service.IQqchCompleteDesignHandoverService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize(hasPermi = "qqchCompleteDesignHandover:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchCompleteDesignHandoverVo qqchCompleteDesignHandoverVo) {
        qqchCompleteDesignHandoverService.save(qqchCompleteDesignHandoverVo);
        return AjaxResult.success(qqchCompleteDesignHandoverVo);
    }

    /**
     * 确认
     * @param qqchCompleteDesignHandoverVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchCompleteDesignHandover:save")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchCompleteDesignHandoverVo qqchCompleteDesignHandoverVo) {
        qqchCompleteDesignHandoverService.confirm(qqchCompleteDesignHandoverVo);
        return AjaxResult.success(qqchCompleteDesignHandoverVo);
    }
}
