package com.hhwy.pm.qqch.preparation.quality.problem.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.QqchQualityProblemControl;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-03 14:30:43
 * @remark 质量通病控制措施
 */
@Data
public class QqchQualityProblemControlVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：质量通病控制措施集合
     */
    private List<QqchQualityProblemControl> list;
}
