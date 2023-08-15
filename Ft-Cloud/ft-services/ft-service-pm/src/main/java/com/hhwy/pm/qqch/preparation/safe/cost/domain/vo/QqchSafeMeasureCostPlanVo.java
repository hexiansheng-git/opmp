package com.hhwy.pm.qqch.preparation.safe.cost.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.cost.domain.QqchSafeMeasureCostPlan;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-10 16:38:50
 * @remark 安全文明措施费策划
 */
@Data
public class QqchSafeMeasureCostPlanVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：预计投入金额（万美元）
     */
    private BigDecimal expectInvestCostTotal;

    /**
     * 字段描述：占工程造价百分比(%)
     */
    private BigDecimal projectCostPercentage;

    /**
     * 字段描述：安全文明措施费策划集合
     */
    private List<QqchSafeMeasureCostPlan> list;
}
