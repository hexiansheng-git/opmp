package com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchChangeProcedurePlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:34
 * @remark 变更程序策划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchChangeProcedurePlanVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）
     */
    private String stageIdentity;
    /**
     * 字段描述：确认状态（0：未确认，1：已确认）
     */
    private String confirmStatus;
    /**
     * 字段描述：版本
     */
    private BigDecimal version;
    /**
     * 字段描述：附件组id
     */
    private String fileGroupId;
    /**
     * 字段描述：变更程序策划集合
     */
    private List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList;
}
