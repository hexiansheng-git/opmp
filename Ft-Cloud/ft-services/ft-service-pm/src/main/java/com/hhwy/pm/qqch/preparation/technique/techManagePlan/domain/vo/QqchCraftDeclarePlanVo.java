package com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchCraftDeclarePlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:40:02
 * @remark 工艺工法申报计划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchCraftDeclarePlanVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：工艺工法申报计划集合
     */
    private List<QqchCraftDeclarePlan> list;
}
