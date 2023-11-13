package com.hhwy.pm.jdgl.diff.analysis.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisSv;
import com.hhwy.pm.jdgl.statistics.domain.PlanStatisticsPeriodValueVO;

/**
 * @author 陈锦豪
 * @date 2023-08-28 16:24:27
 * @remark
 */
public interface IJdglDiffAnalysisSvService {

    JdglDiffAnalysisSv getJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv);

    List<JdglDiffAnalysisSv> getJdglDiffAnalysisSvList(JdglDiffAnalysisSv jdglDiffAnalysisSv);

    int insertJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv);

    int insertJdglDiffAnalysisSvList(List<JdglDiffAnalysisSv> jdglDiffAnalysisSvList);

    int updateJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv);

    int updateJdglDiffAnalysisSvList(List<JdglDiffAnalysisSv> jdglDiffAnalysisSvList);

    int deleteJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv);

    int deleteJdglDiffAnalysisSvByPks(List<Long> jdglDiffAnalysisSvPkList);

    BigDecimal initJdglDiffAnalysisSv(JdglDiffAnalysis jdglDiffAnalysis);

    Map<String, Object> getPlanAndComp(JdglDiffAnalysisSv jdglDiffAnalysisSvParam);

    List<JdglDiffAnalysisSv> getJdglDiffAnalysisSvLazyList(JdglDiffAnalysisSv jdglDiffAnalysisSvParam);

    void deleteJdglDiffAnalysisSvByDiffAnalysisId(Long diffAnalysisId);
}
