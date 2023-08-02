package com.hhwy.pm.qqch.preparation.finance.contract.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.finance.contract.domain.vo.QqchFinancialMainContractTermsVo;
import com.hhwy.pm.qqch.preparation.finance.contract.service.IQqchFinancialMainContractTermsService;
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
 * @date 2023-08-02 14:24:03
 * @remark 10.1财务相关主合同条款
 */
@Validated
@RestController
@RequestMapping("/qqchFinancialMainContractTerms")
public class QqchFinancialMainContractTermsController extends BaseController {

    @Autowired
    private IQqchFinancialMainContractTermsService qqchFinancialMainContractTermsService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchFinancialMainContractTerms:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchFinancialMainContractTermsVo qqchFinancialMainContractTermsVo = qqchFinancialMainContractTermsService
            .getQqchFinancialMainContractTermsList(version);
        return AjaxResult.success(qqchFinancialMainContractTermsVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchFinancialMainContractTermsVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchFinancialMainContractTerms:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchFinancialMainContractTermsVo qqchFinancialMainContractTermsVo) {
        qqchFinancialMainContractTermsService.batchSave(qqchFinancialMainContractTermsVo);
        return AjaxResult.success();
    }
}
