package com.hhwy.pm.qqch.preparation.finance.policy.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchMainTaxItemRateVo;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchMainTaxItemRateService;
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
 * @date 2023-08-01 16:03:00
 * @remark 10.2.2主要税目税率
 */
@Validated
@RestController
@RequestMapping("/qqchMainTaxItemRate")
public class QqchMainTaxItemRateController extends BaseController {

    @Autowired
    private IQqchMainTaxItemRateService qqchMainTaxItemRateService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMainTaxItemRate:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchMainTaxItemRateVo qqchMainTaxItemRateVo = qqchMainTaxItemRateService.getQqchMainTaxItemRateList(version);
        return AjaxResult.success(qqchMainTaxItemRateVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchMainTaxItemRateVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMainTaxItemRate:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(@RequestBody QqchMainTaxItemRateVo qqchMainTaxItemRateVo) {
        qqchMainTaxItemRateService.batchSave(qqchMainTaxItemRateVo);
        return AjaxResult.success();
    }
}
