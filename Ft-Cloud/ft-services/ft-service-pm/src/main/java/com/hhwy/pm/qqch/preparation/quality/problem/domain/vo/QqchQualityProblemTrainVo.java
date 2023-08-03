package com.hhwy.pm.qqch.preparation.quality.problem.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.QqchQualityProblemTrain;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-03 14:30:51
 * @remark 质量通病培训策划
 */
@Data
public class QqchQualityProblemTrainVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：质量通病培训策划集合
     */
    private List<QqchQualityProblemTrain> list;
}
