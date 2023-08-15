package com.hhwy.pm.qqch.preparation.costControl.postDuty.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.domain.QqchCostControlPostDuty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-08 17:48:45
 * @remark 成本管控岗位责任Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchCostControlPostDutyVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    private List<QqchCostControlPostDuty> list;
}
