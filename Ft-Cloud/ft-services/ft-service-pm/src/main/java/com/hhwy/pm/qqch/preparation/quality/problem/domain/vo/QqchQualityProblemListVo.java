package com.hhwy.pm.qqch.preparation.quality.problem.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.QqchQualityProblemList;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-03 14:30:34
 * @remark 质量通病清单
 */
@Data
public class QqchQualityProblemListVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：质量通病清单集合
     */
    private List<QqchQualityProblemList> list;
}
