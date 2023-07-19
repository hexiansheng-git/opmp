package com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionReviewPlan;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @author zhenlili
 * @date 2023-07-17 15:32:17
 * @remark qqch_construction_review_plan
 */
@Data
public class QqchConstructionReviewPlanVo {

    private static final long serialVersionUID = 1L;

    /**
     * 阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）
     */
    @JsonProperty
    private String stageIdentity;

    /**
     * 版本状态
     */
    @JsonProperty
    private BigDecimal version;

    /**
     * 字段描述：确认状态（0：未确认，1：已确认）
     */
    private String confirmStatus;

    /**
     * 字段描述：施工方案编审计划集合
     */
    private List<QqchConstructionReviewPlan> list;
}
