package com.hhwy.pm.qqch.preparation.finance.policy.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchMainTaxItemRate;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-01 16:02:35
 * @remark 主要税目税率
 */
@Data
public class QqchMainTaxItemRateVo extends PreparationEntity {

    /**
     * 字段描述：主要税目税率集合
     */
    private List<QqchMainTaxItemRate> list;
}
