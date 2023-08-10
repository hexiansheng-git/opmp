package com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.QqchSecondManageKeyPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:47:11
 * @remark qqch_second_manage_key_point
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchSecondManageKeyPointVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    private List<QqchSecondManageKeyPoint> list;
}
