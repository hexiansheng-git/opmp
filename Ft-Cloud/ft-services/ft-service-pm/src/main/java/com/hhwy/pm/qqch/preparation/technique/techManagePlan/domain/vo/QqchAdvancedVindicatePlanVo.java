package com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAdvancedVindicatePlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-27 15:51:15
 * @remark 高新维护计划Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchAdvancedVindicatePlanVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：年份集合
     */
    private List<String> vintageList;
    /**
     * 字段描述：高新维护计划集合
     */
    private List<QqchAdvancedVindicatePlan> qqchAdvancedVindicatePlanList;
}
