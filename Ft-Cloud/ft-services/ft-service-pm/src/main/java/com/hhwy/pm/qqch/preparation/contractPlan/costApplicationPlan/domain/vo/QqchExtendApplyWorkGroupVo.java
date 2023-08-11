package com.hhwy.pm.qqch.preparation.contractPlan.costApplicationPlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.contractPlan.costApplicationPlan.domain.QqchExtendApplyWorkGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-08 17:48:45
 * @remark qqch_extend_apply_work_group
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchExtendApplyWorkGroupVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    private List<QqchExtendApplyWorkGroup> list;
}
