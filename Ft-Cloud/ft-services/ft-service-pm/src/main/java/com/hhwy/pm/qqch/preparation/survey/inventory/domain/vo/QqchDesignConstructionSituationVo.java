package com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchDesignConstructionSituation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-13 10:09:03
 * @remark 边设计边施工情况Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchDesignConstructionSituationVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    private List<QqchDesignConstructionSituation> qqchDesignConstructionSituationList;
}
