package com.hhwy.pm.qqch.preparation.safe.organ.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.QqchSpecialPersonControlPlan;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-07 13:50:54
 * @remark 特种作业人员管控策划
 */
@Data
public class QqchSpecialPersonControlPlanVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：特种作业人员管控策划集合
     */
    private List<QqchSpecialPersonControlPlan> list;
}
