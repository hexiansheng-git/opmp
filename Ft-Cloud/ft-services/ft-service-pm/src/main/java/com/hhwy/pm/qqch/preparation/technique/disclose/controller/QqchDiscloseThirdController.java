package com.hhwy.pm.qqch.preparation.technique.disclose.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo.QqchDiscloseThirdVo;
import com.hhwy.pm.qqch.preparation.technique.disclose.service.IQqchDiscloseThirdService;
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
 * @date 2023-07-21 14:26:42
 * @remark 3.5.2三级交底
 */
@Validated
@RestController
@RequestMapping("/qqchDiscloseThird")
public class QqchDiscloseThirdController extends BaseController {

    @Autowired
    private IQqchDiscloseThirdService qqchDiscloseThirdService;

    @PreAuthorize(hasPermi = "qqchDiscloseThird:list")
    @GetMapping("/getTreeList")
    public AjaxResult getTreeList(BigDecimal version) {
        QqchDiscloseThirdVo qqchDiscloseThirdVo = qqchDiscloseThirdService.getQqchDiscloseThirdList(version);
        return AjaxResult.success(qqchDiscloseThirdVo);
    }

    @PreAuthorize(hasPermi = "qqchDiscloseThird:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchDiscloseThirdVo qqchDiscloseThirdVo) {
        qqchDiscloseThirdService.batchSave(qqchDiscloseThirdVo);
        return AjaxResult.success();
    }
}
