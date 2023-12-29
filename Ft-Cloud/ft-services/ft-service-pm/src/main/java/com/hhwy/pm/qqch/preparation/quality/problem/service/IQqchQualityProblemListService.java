package com.hhwy.pm.qqch.preparation.quality.problem.service;

import com.hhwy.pm.qqch.preparation.quality.problem.domain.QqchQualityProblemList;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.vo.QqchQualityProblemListVo;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-03 14:30:34
 * @remark 9.2.1 质量通病清单
 */
public interface IQqchQualityProblemListService {

    QqchQualityProblemListVo getQqchQualityProblemListList(BigDecimal version);

    List<QqchQualityProblemList> getListByVersion(BigDecimal version);

    void batchSave(QqchQualityProblemListVo qqchQualityProblemListVo);

    /**
     * 推送质量通病到总部版
     * @param version
     */
    void pushQyzsQualityCommonProblem(BigDecimal version);
}
