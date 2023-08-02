package com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionReviewPlan;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-17 15:32:17
 * @remark qqch_construction_review_plan
 */
@Data
public class QqchConstructionReviewPlanVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：施工方案编审计划集合
     */
    private List<QqchConstructionReviewPlan> list;
}
