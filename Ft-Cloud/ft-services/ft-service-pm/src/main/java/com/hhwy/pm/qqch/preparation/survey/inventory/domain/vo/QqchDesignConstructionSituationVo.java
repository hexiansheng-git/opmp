package com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo;

import com.hhwy.pm.qqch.constant.ConfirmStatus;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchDesignConstructionSituation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-07-13 10:09:03
 * @remark 边设计边施工情况Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchDesignConstructionSituationVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）
     */
    private String stageIdentity;
    /**
     * 字段描述：确认状态（0：未确认，1：已确认）
     */
    private String confirmStatus = ConfirmStatus.UNCONFIRMED;
    /**
     * 字段描述：版本
     */
    private BigDecimal version;
    /**
     * 字段描述：
     */
    private List<QqchDesignConstructionSituation> qqchDesignConstructionSituationList;
}
