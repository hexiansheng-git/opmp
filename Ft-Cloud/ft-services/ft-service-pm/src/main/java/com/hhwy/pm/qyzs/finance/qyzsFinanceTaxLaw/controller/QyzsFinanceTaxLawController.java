package com.hhwy.pm.qyzs.finance.qyzsFinanceTaxLaw.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTaxLaw.domain.FinanceTaxLawQueryVo;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTaxLaw.service.IQyzsFinanceTaxLawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjh
 * @date 2023-11-27 16:01:30
 * @remark 财务知识库-税法库
 */
@Validated
@RestController
@RequestMapping("/qyzsFinanceTaxLaw")
public class QyzsFinanceTaxLawController extends BaseController {

    @Autowired
    private IQyzsFinanceTaxLawService qyzsFinanceTaxLawService;


    @GetMapping("/list")
    public AjaxResult getQyzsFinanceTaxLawList(FinanceTaxLawQueryVo queryVo) {
        return qyzsFinanceTaxLawService.getQyzsFinanceTaxLawList(queryVo);
    }
}
