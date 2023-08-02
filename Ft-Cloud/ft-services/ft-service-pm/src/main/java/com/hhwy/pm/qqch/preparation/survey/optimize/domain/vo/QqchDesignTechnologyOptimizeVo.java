package com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchDesignTechnologyOptimize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:48
 * @remark 设计技术优化要点
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchDesignTechnologyOptimizeVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：设计技术优化要点集合
     */
    private List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeList;
}
