package com.hhwy.pm.qqch.preparation.quality.duty.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.QqchPersonControlPlan;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-03 14:29:43
 * @remark 人员管控策划
 */
@Data
public class QqchPersonControlPlanVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：人员管控策划集合
     */
    private List<QqchPersonControlPlan> list;
}
