package com.hhwy.pm.qqch.preparation.finance.policy.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchLocalBankSituationVo;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchLocalBankSituationService;
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
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchLocalBankSituationVo qqchLocalBankSituationVo) {
        qqchLocalBankSituationService.batchSave(qqchLocalBankSituationVo);
        return AjaxResult.success();
    }
}
