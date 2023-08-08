package com.hhwy.pm.qqch.preparation.safe.danger.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerProcessControlPlan;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-07 14:24:24
 * @remark 危大工程过程管控策划
 */
@Data
public class QqchDangerProcessControlPlanVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 字段描述：危大工程过程管控策划集合
     */
    private List<QqchDangerProcessControlPlan> list;
}
