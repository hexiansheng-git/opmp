package com.hhwy.pm.qqch.preparation.technique.difficulty.service;

import com.hhwy.pm.qqch.preparation.technique.difficulty.domain.vo.QqchTechKeyDifficultAnalysisVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-07-10 14:20:39
 * @remark 3.2施工技术重难点分析
 */
public interface IQqchTechKeyDifficultAnalysisService {

    QqchTechKeyDifficultAnalysisVo getQqchTechKeyDifficultAnalysisList(BigDecimal version);

    void batchSave(QqchTechKeyDifficultAnalysisVo qqchTechKeyDifficultAnalysisVo);
}
