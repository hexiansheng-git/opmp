package com.hhwy.pm.qqch.preparation.technique.difficulty.service;

import com.hhwy.pm.qqch.preparation.technique.difficulty.domain.QqchTechKeyDifficultAnalysis;
import com.hhwy.pm.qqch.preparation.technique.difficulty.domain.QqchTechKeyDifficultAnalysisVo;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-10 14:20:39
 * @remark 3.2施工技术重难点分析
 */
public interface IQqchTechKeyDifficultAnalysisService {

    QqchTechKeyDifficultAnalysisVo getQqchTechKeyDifficultAnalysisList(
        QqchTechKeyDifficultAnalysis qqchTechKeyDifficultAnalysis);

    void batchSave(QqchTechKeyDifficultAnalysisVo qqchTechKeyDifficultAnalysisVo);

    int deleteQqchTechKeyDifficultAnalysisByPks(List<Long> qqchTechKeyDifficultAnalysisPkList);
}
