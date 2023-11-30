package com.hhwy.pm.qyzs.finance.qyzsFinanceBankStatus.controller;


import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceBankStatus.domain.FinanceBankStatusQueryVo;
import com.hhwy.pm.qyzs.finance.qyzsFinanceBankStatus.service.IQyzsFinanceBankStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjh
 * @date 2023-11-27 16:41:22
 * @remark 财务知识库-当地银行状况
 */
@Validated
@RestController
@RequestMapping("/qyzsFinanceBankStatus")
public class QyzsFinanceBankStatusController extends BaseController {

    @Autowired
    private IQyzsFinanceBankStatusService qyzsFinanceBankStatusService;


    @GetMapping("/list")
    public AjaxResult getQyzsFinanceBankStatusList(FinanceBankStatusQueryVo queryVo) {
        return qyzsFinanceBankStatusService.getQyzsFinanceBankStatusList(queryVo);
    }
}
