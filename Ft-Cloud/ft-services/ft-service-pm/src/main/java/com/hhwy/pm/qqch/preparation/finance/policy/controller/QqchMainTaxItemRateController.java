package com.hhwy.pm.qqch.preparation.finance.policy.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchMainTaxItemRateVo;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchMainTaxItemRateService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
    @CustomLogger(title = "前期策划-前期策划编制-财务策划-10.2税务、会计、金融政策", name = "\n" +
            "10.2.2 主要税目税率" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(@RequestBody QqchMainTaxItemRateVo qqchMainTaxItemRateVo) {
        qqchMainTaxItemRateService.batchSave(qqchMainTaxItemRateVo);
        return AjaxResult.success();
    }
}
