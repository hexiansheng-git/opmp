package com.hhwy.pm.qqch.preparation.finance.policy.service;

import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchLocalBankSituationVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-03 13:45:08
 * @remark 10.2.5当地银行情况描述
 */
public interface IQqchLocalBankSituationService {

    QqchLocalBankSituationVo getQqchLocalBankSituationList(BigDecimal version);

    void batchSave(QqchLocalBankSituationVo qqchLocalBankSituationVo);
}
