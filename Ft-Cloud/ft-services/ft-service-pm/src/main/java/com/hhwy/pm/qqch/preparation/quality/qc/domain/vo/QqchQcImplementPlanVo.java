package com.hhwy.pm.qqch.preparation.quality.qc.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.QqchQcImplementPlan;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-04 10:30:39
 * @remark QC实施计划
 */
@Data
public class QqchQcImplementPlanVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：QC实施计划集合
     */
    private List<QqchQcImplementPlan> list;
}
