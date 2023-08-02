package com.hhwy.pm.qqch.preparation.finance.policy.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchLocalAccountingPolicy;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-02 14:24:44
 * @remark 10.2.3当地会计政策描述
 */
@Data
public class QqchLocalAccountingPolicyVo extends PreparationEntity {

    /**
     * 字段描述：当地会计政策描述集合
     */
    private List<QqchLocalAccountingPolicy> list;
}
