package com.hhwy.pm.qqch.preparation.technique.difficulty.mapper;

import com.hhwy.pm.qqch.preparation.technique.difficulty.domain.QqchTechKeyDifficultAnalysis;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-07-10 14:20:39
 * @remark 3.2施工技术重难点分析
 */
public interface QqchTechKeyDifficultAnalysisMapper {

    QqchTechKeyDifficultAnalysis getQqchTechKeyDifficultAnalysis(
        QqchTechKeyDifficultAnalysis qqchTechKeyDifficultAnalysis);

    List<QqchTechKeyDifficultAnalysis> getQqchTechKeyDifficultAnalysisList(
        QqchTechKeyDifficultAnalysis qqchTechKeyDifficultAnalysis);

    int insertQqchTechKeyDifficultAnalysis(QqchTechKeyDifficultAnalysis qqchTechKeyDifficultAnalysis);

    int insertQqchTechKeyDifficultAnalysisList(
        @Param("qqchTechKeyDifficultAnalysisList") List<QqchTechKeyDifficultAnalysis> qqchTechKeyDifficultAnalysisList);

    int updateQqchTechKeyDifficultAnalysis(QqchTechKeyDifficultAnalysis qqchTechKeyDifficultAnalysis);

    int updateQqchTechKeyDifficultAnalysisList(
        @Param("list") List<QqchTechKeyDifficultAnalysis> qqchTechKeyDifficultAnalysisList);

    int deleteQqchTechKeyDifficultAnalysis(QqchTechKeyDifficultAnalysis qqchTechKeyDifficultAnalysis);

    int deleteQqchTechKeyDifficultAnalysisByPks(
        @Param("qqchTechKeyDifficultAnalysisPkList") List<Long> qqchTechKeyDifficultAnalysisPkList);
}
