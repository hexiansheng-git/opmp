package com.hhwy.pm.qyzs.finance.qyzsFinanceTaxItemRate.controller;


import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTaxItemRate.domain.FinanceTaxItemRateQueryVo;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTaxItemRate.service.IQyzsFinanceTaxItemRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjh
 * @date 2023-11-27 16:01:25
 * @remark 财务知识库-主要税目税率
 */
@Validated
@RestController
@RequestMapping("/qyzsFinanceTaxItemRate")
public class QyzsFinanceTaxItemRateController extends BaseController {

    @Autowired
    private IQyzsFinanceTaxItemRateService qyzsFinanceTaxItemRateService;


    @GetMapping("/list")
    public AjaxResult getQyzsFinanceTaxItemRateList(FinanceTaxItemRateQueryVo queryVo) {
        return qyzsFinanceTaxItemRateService.getQyzsFinanceTaxItemRateList(queryVo);
    }
}
