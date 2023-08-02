package com.hhwy.pm.qqch.preparation.technique.bimTechPlan.domain.vo;

import com.hhwy.constant.CommonYesNo;
import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.domain.QqchBimTechPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:45:57
 * @remark  BIM技术策划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchBimTechPlanVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：本项目是否应用BIM技术（1：是，0：否）
     */
    private String bimMark = CommonYesNo.NO;
    /**
     * 字段描述：BIM技术策划集合
     */
    private List<QqchBimTechPlan> qqchBimTechPlanList;
}
