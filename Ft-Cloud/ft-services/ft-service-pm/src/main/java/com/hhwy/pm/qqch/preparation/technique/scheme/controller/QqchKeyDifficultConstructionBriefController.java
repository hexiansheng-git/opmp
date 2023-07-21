package com.hhwy.pm.qqch.preparation.technique.scheme.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchKeyDifficultConstructionBriefVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchKeyDifficultConstructionBriefService;
import com.hhwy.utils.validation.ValidationGroups;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PreAuthorize(hasPermi = "qqchKeyDifficultConstructionBrief:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchKeyDifficultConstructionBriefVo qqchKeyDifficultConstructionBriefVo = qqchKeyDifficultConstructionBriefService
            .getQqchKeyDifficultConstructionBriefList(version);
        return AjaxResult.success(qqchKeyDifficultConstructionBriefVo);
    }

    @PreAuthorize(hasPermi = "qqchKeyDifficultConstructionBrief:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchKeyDifficultConstructionBriefVo qqchKeyDifficultConstructionBriefVo) {
        qqchKeyDifficultConstructionBriefService.batchSave(qqchKeyDifficultConstructionBriefVo);
        return AjaxResult.success();
    }
}
