package com.hhwy.pm.qqch.preparation.finance.policy.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchLocalBankSituation;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-03 13:45:08
 * @remark qqch_local_bank_situation
 */
@Data
public class QqchLocalBankSituationVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：当地银行情况描述集合
     */
    private List<QqchLocalBankSituation> list;
}
