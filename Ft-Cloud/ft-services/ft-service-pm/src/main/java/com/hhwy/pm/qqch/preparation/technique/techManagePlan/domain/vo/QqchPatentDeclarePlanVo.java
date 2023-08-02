package com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchPatentDeclarePlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:40:39
 * @remark 专利申报计划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchPatentDeclarePlanVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：专利申报计划集合
     */
    private List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList;
}
