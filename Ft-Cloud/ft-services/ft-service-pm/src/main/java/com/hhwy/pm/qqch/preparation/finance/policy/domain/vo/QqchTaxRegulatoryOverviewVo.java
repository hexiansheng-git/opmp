package com.hhwy.pm.qqch.preparation.finance.policy.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchTaxLaw;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchTaxRegulatoryOverview;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-01 16:02:35
 * @remark 税务监管环境概述
 */
@Data
public class QqchTaxRegulatoryOverviewVo extends PreparationEntity {

    /**
     * 税务监管环境概述
     */
    private QqchTaxRegulatoryOverview overview;

    /**
     * 字段描述：税法集合
     */
    private List<QqchTaxLaw> list;
}
