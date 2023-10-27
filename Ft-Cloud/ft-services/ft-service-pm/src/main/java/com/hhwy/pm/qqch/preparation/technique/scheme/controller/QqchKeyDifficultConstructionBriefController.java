package com.hhwy.pm.qqch.preparation.technique.scheme.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchKeyDifficultConstructionBriefVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchKeyDifficultConstructionBriefService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-07-17 15:29:49
 * @remark 3.4.4重难点分项施工方案简述
 */
@Validated
@RestController
@RequestMapping("/qqchKeyDifficultConstructionBrief")
public class QqchKeyDifficultConstructionBriefController extends BaseController {

    @Autowired
    private IQqchKeyDifficultConstructionBriefService qqchKeyDifficultConstructionBriefService;

    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchKeyDifficultConstructionBriefVo qqchKeyDifficultConstructionBriefVo = qqchKeyDifficultConstructionBriefService
            .getQqchKeyDifficultConstructionBriefList(version);
        return AjaxResult.success(qqchKeyDifficultConstructionBriefVo);
    }

    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchKeyDifficultConstructionBriefVo qqchKeyDifficultConstructionBriefVo) {
        qqchKeyDifficultConstructionBriefService.batchSave(qqchKeyDifficultConstructionBriefVo);
        return AjaxResult.success();
    }
}
