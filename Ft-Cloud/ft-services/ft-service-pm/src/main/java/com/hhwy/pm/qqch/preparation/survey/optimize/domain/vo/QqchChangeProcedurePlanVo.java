package com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchChangeProcedurePlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:34
 * @remark 变更程序策划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchChangeProcedurePlanVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：附件组id
     */
    private String fileGroupId;
    /**
     * 字段描述：变更程序策划集合
     */
    private List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList;
}
