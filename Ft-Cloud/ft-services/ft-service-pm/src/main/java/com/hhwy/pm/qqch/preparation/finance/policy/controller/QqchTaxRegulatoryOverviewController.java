package com.hhwy.pm.qqch.preparation.finance.policy.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchTaxRegulatoryOverviewVo;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchTaxRegulatoryOverviewService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
    @CustomLogger(title = "前期策划-前期策划编制-财务策划-10.2税务、会计、金融政策", name = "\n" +
            "10.2.1 税务监管环境概述" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(@RequestBody QqchTaxRegulatoryOverviewVo qqchTaxRegulatoryOverviewVo) {
        qqchTaxRegulatoryOverviewService.batchSave(qqchTaxRegulatoryOverviewVo);
        return AjaxResult.success();
    }
}
