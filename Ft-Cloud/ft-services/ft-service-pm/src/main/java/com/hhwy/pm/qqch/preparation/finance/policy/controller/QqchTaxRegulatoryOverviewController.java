package com.hhwy.pm.qqch.preparation.finance.policy.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchTaxRegulatoryOverviewVo;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchTaxRegulatoryOverviewService;
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
 * @date 2023-08-01 16:02:35
 * @remark 10.2.1税务监管环境概述
 */
@Validated
@RestController
@RequestMapping("/qqchTaxRegulatoryOverview")
public class QqchTaxRegulatoryOverviewController extends BaseController {

    @Autowired
    private IQqchTaxRegulatoryOverviewService qqchTaxRegulatoryOverviewService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchTaxRegulatoryOverview:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchTaxRegulatoryOverviewVo qqchTaxRegulatoryOverviewVo = qqchTaxRegulatoryOverviewService
            .getQqchTaxRegulatoryOverview(version);
        return AjaxResult.success(qqchTaxRegulatoryOverviewVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchTaxRegulatoryOverviewVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchTaxRegulatoryOverview:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(@RequestBody QqchTaxRegulatoryOverviewVo qqchTaxRegulatoryOverviewVo) {
        qqchTaxRegulatoryOverviewService.batchSave(qqchTaxRegulatoryOverviewVo);
        return AjaxResult.success();
    }
}
