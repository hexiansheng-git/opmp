package com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchTopicResearchPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:39:25
 * @remark 课题研究计划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchTopicResearchPlanVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：优课题研究计划集合
     */
    private List<QqchTopicResearchPlan> list;
}
