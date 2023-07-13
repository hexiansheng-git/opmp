package com.hhwy.pm.qqch.preparation.survey.inventory.controller;

import java.util.Arrays;
import java.util.List;
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


    /**
     * 边设计边施工情况台账
     * @param qqchDesignConstructionSituationParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignConstructionSituation:list")
    @GetMapping("/list")
    public AjaxResult getQqchDesignConstructionSituationList(@Validated(ValidationGroups.Select.class) @RequestBody QqchDesignConstructionSituation qqchDesignConstructionSituationParam) {
        QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo = qqchDesignConstructionSituationService.getQqchDesignConstructionSituationVo(qqchDesignConstructionSituationParam);
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

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignConstructionSituation:remove")
    @PostMapping("/remove/{ids}")
    public AjaxResult deleteQqchDesignConstructionSituationByPks(@PathVariable Long[] ids) {
        List<Long> qqchDesignConstructionSituationPkList = Arrays.asList(ids);
        return toAjax(qqchDesignConstructionSituationService.deleteQqchDesignConstructionSituationByPks(qqchDesignConstructionSituationPkList));
    }
}
