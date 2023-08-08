package com.hhwy.pm.qqch.preparation.safe.organ.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.QqchSafeOrganDutyPlan;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-07 13:49:43
 * @remark 安全组织职责策划
 */
@Data
public class QqchSafeOrganDutyPlanVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：安全组织职责策划集合
     */
    private List<QqchSafeOrganDutyPlan> list;
}
