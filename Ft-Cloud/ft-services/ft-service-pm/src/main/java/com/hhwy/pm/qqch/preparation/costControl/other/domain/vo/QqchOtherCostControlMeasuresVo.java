package com.hhwy.pm.qqch.preparation.costControl.other.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.costControl.other.domain.QqchOtherCostControlMeasures;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-11 09:38:56
 * @remark 其他成本管控工作安排及措施
 */
@Data
public class QqchOtherCostControlMeasuresVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 字段描述：其他成本管控工作安排及措施集合
     */
    private List<QqchOtherCostControlMeasures> treeList;
}
