package com.hhwy.pm.qqch.preparation.technique.difficulty.service;

import com.hhwy.pm.qqch.preparation.technique.difficulty.domain.vo.QqchTechKeyDifficultAnalysisVo;

/**
 * @author zhenglili
 * @date 2023-07-10 14:20:39
 * @remark 3.2施工技术重难点分析
 */
public interface IQqchTechKeyDifficultAnalysisService {

    QqchTechKeyDifficultAnalysisVo getQqchTechKeyDifficultAnalysisList();

    void batchSave(QqchTechKeyDifficultAnalysisVo qqchTechKeyDifficultAnalysisVo);
}
