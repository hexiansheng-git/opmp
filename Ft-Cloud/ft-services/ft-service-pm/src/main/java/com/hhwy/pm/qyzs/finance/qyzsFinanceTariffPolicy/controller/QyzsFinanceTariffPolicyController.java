package com.hhwy.pm.qyzs.finance.qyzsFinanceTariffPolicy.controller;


import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTariffPolicy.domain.FinanceTariffPolicyQueryVo;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTariffPolicy.service.IQyzsFinanceTariffPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjh
 * @date 2023-11-27 16:41:26
 * @remark 财务知识库-当地关税政策
 */
@Validated
@RestController
@RequestMapping("/qyzsFinanceTariffPolicy")
public class QyzsFinanceTariffPolicyController extends BaseController {

    @Autowired
    private IQyzsFinanceTariffPolicyService qyzsFinanceTariffPolicyService;


    @GetMapping("/list")
    public AjaxResult getQyzsFinanceTariffPolicyList(FinanceTariffPolicyQueryVo queryVo) {
        return qyzsFinanceTariffPolicyService.getQyzsFinanceTariffPolicyList(queryVo);
    }
}
