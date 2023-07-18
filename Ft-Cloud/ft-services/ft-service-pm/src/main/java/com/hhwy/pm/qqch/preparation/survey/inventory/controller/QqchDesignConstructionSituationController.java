package com.hhwy.pm.qqch.preparation.survey.inventory.controller;

import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchDesignConstructionSituation;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo.QqchDesignConstructionSituationVo;
import com.hhwy.pm.qqch.preparation.survey.inventory.service.IQqchDesignConstructionSituationService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author han
 * @date 2023-07-13 10:09:03
 * @remark 边设计边施工情况
 */
@Validated
@RestController
@RequestMapping("/qqchDesignConstructionSituation")
public class QqchDesignConstructionSituationController extends BaseController {

    @Autowired
    private IQqchDesignConstructionSituationService qqchDesignConstructionSituationService;

    @Autowired
    private CommonMapper commonMapper;


    /**
     * 边设计边施工情况台账
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignConstructionSituation:list")
    @GetMapping("/list")
    public AjaxResult getQqchDesignConstructionSituationList() {
        QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo = qqchDesignConstructionSituationService.getQqchDesignConstructionSituationVo();
        return AjaxResult.success(qqchDesignConstructionSituationVo);
    }

    /**
     * 保存
     * @param qqchDesignConstructionSituationVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignConstructionSituation:update")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Update.class) @RequestBody QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo) {
        qqchDesignConstructionSituationService.save(qqchDesignConstructionSituationVo);
        return AjaxResult.success(qqchDesignConstructionSituationVo);
    }

    /**
     * 确认
     * @param qqchDesignConstructionSituationVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignConstructionSituation:update")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo) {
        qqchDesignConstructionSituationService.confirm(qqchDesignConstructionSituationVo);
        return AjaxResult.success(qqchDesignConstructionSituationVo);
    }
}
