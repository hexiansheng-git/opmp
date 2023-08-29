package com.hhwy.pm.jdgl.diff.analysis.service;

import java.util.List;

import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;

/**
 * @author 陈锦豪
 * @date 2023-08-28 15:06:24
 * @remark
 */
public interface IJdglDiffAnalysisService {

    JdglDiffAnalysis getJdglDiffAnalysis(JdglDiffAnalysis jdglDiffAnalysis);

    List<JdglDiffAnalysis> getJdglDiffAnalysisList(JdglDiffAnalysis jdglDiffAnalysis);

    int insertJdglDiffAnalysis(JdglDiffAnalysis jdglDiffAnalysis);

    int insertJdglDiffAnalysisList(List<JdglDiffAnalysis> jdglDiffAnalysisList);

    int updateJdglDiffAnalysis(JdglDiffAnalysis jdglDiffAnalysis);

    int updateJdglDiffAnalysisList(List<JdglDiffAnalysis> jdglDiffAnalysisList);

    int deleteJdglDiffAnalysis(JdglDiffAnalysis jdglDiffAnalysis);

    int deleteJdglDiffAnalysisByPks(List<Long> jdglDiffAnalysisPkList);
}
