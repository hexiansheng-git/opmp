package com.hhwy.pm.qqch.preparation.survey.inventory.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo.QqchDesignConstructionSituationVo;
import com.hhwy.pm.qqch.preparation.survey.inventory.service.IQqchDesignConstructionSituationService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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


    /**
     * 边设计边施工情况台账
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getQqchDesignConstructionSituationList(BigDecimal version) {
        QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo = qqchDesignConstructionSituationService.getQqchDesignConstructionSituationVo(version);
        return AjaxResult.success(qqchDesignConstructionSituationVo);
    }

    /**
     * 保存
     * @param qqchDesignConstructionSituationVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchDesignConstructionSituation:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo) {
        qqchDesignConstructionSituationService.save(qqchDesignConstructionSituationVo);
        return AjaxResult.success(qqchDesignConstructionSituationVo);
    }

    /**
     * 确认
     * @param qqchDesignConstructionSituationVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchDesignConstructionSituation:save")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Update.class) @RequestBody QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo) {
        qqchDesignConstructionSituationService.confirm(qqchDesignConstructionSituationVo);
        return AjaxResult.success(qqchDesignConstructionSituationVo);
    }
}
