package com.hhwy.pm.qqch.preparation.safe.risk.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskControlPlan;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-07 13:53:33
 * @remark 安全风险过程管控策划
 */
@Data
public class QqchSafeRiskControlPlanVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：安全风险过程管控策划
     */
    private List<QqchSafeRiskControlPlan> list;
}
