package com.hhwy.pm.qqch.preparation.quality.problem.service;

import com.hhwy.pm.qqch.preparation.quality.problem.domain.vo.QqchQualityProblemTrainVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-03 14:30:51
 * @remark 9.2.3 质量通病培训策划
 */
public interface IQqchQualityProblemTrainService {

    QqchQualityProblemTrainVo getQqchQualityProblemTrainList(BigDecimal version);

    void batchSave(QqchQualityProblemTrainVo qqchQualityProblemTrainVo);
}
