package com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.domain.SafeSafeRiskQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.service.IQyzsSafeSafeRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjh
 * @date 2023-11-17 16:26:05
 * @remark 安全知识库-安全风险源库
 */
@Validated
@RestController
@RequestMapping("/qyzsSafeSafeRisk")
public class QyzsSafeSafeRiskController extends BaseController {

    @Autowired
    private IQyzsSafeSafeRiskService qyzsSafeSafeRiskService;


    @GetMapping("/list")
    public AjaxResult getQyzsSafeSafeRiskList(SafeSafeRiskQueryVo queryVo) {
        return qyzsSafeSafeRiskService.getQyzsSafeSafeRiskList(queryVo);
    }
}
