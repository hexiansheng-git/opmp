package com.hhwy.pm.qyzs.finance.qyzsFinanceAccountPolicy.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceAccountPolicy.domain.FinanceAccountPolicyQueryVo;
import com.hhwy.pm.qyzs.finance.qyzsFinanceAccountPolicy.service.IQyzsFinanceAccountPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cj
 * @date 2023-11-27 16:01:11
 * @remark 财务知识库-当地会计政策
 */
@Validated
@RestController
@RequestMapping("/qyzsFinanceAccountPolicy")
public class QyzsFinanceAccountPolicyController extends BaseController {

    @Autowired
    private IQyzsFinanceAccountPolicyService qyzsFinanceAccountPolicyService;


//    @PreAuthorize(hasPermi = "qyzsFinanceAccountPolicy:list")
    @GetMapping("/list")
    public AjaxResult getQyzsFinanceAccountPolicyList(FinanceAccountPolicyQueryVo queryVo) {
        return qyzsFinanceAccountPolicyService.getQyzsFinanceAccountPolicyList(queryVo);
    }
}
