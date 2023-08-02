package com.hhwy.pm.qqch.preparation.finance.policy.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchLocalTariffPolicy;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-02 14:24:49
 * @remark 10.2.4当地关税政策描述
 */
@Data
public class QqchLocalTariffPolicyVo extends PreparationEntity {

    /**
     * 字段描述：当地关税政策描述集合
     */
    private List<QqchLocalTariffPolicy> list;
}
