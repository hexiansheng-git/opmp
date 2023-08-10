package com.hhwy.pm.qqch.preparation.technique.techTrainPlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.domain.QqchTechTrainPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:57:39
 * @remark 技术培训策划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchTechTrainPlanVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：技术培训策划集合
     */
    private List<QqchTechTrainPlan> list;
}
