package com.hhwy.pm.qqch.preparation.finance.policy.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchLocalBankSituationVo;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchLocalBankSituationService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-03 13:45:08
 * @remark 10.2.5当地银行情况描述
 */
@Validated
@RestController
@RequestMapping("/qqchLocalBankSituation")
public class QqchLocalBankSituationController extends BaseController {

    @Autowired
    private IQqchLocalBankSituationService qqchLocalBankSituationService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchLocalBankSituation:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchLocalBankSituationVo qqchLocalBankSituationVo = qqchLocalBankSituationService
            .getQqchLocalBankSituationList(version);
        return AjaxResult.success(qqchLocalBankSituationVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchLocalBankSituationVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchLocalBankSituation:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-财务策划-10.2税务、会计、金融政策", name = "\n" +
            "10.2.5 当地银行情况描述" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(@RequestBody QqchLocalBankSituationVo qqchLocalBankSituationVo) {
        qqchLocalBankSituationService.batchSave(qqchLocalBankSituationVo);
        return AjaxResult.success();
    }
}
