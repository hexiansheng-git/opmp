package com.hhwy.pm.jdgl.diff.analysis.service;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisPath;

/**
 * @author 陈锦豪
 * @date 2023-08-28 16:24:21
 * @remark
 */
public interface IJdglDiffAnalysisPathService {

    JdglDiffAnalysisPath getJdglDiffAnalysisPath(JdglDiffAnalysisPath jdglDiffAnalysisPath);

    List<JdglDiffAnalysisPath> getJdglDiffAnalysisPathList(JdglDiffAnalysisPath jdglDiffAnalysisPath);

    int insertJdglDiffAnalysisPath(JdglDiffAnalysisPath jdglDiffAnalysisPath);

    int insertJdglDiffAnalysisPathList(List<JdglDiffAnalysisPath> jdglDiffAnalysisPathList);

    int updateJdglDiffAnalysisPath(JdglDiffAnalysisPath jdglDiffAnalysisPath);

    int updateJdglDiffAnalysisPathList(List<JdglDiffAnalysisPath> jdglDiffAnalysisPathList);

    int deleteJdglDiffAnalysisPath(JdglDiffAnalysisPath jdglDiffAnalysisPath);

    int deleteJdglDiffAnalysisPathByPks(List<Long> jdglDiffAnalysisPathPkList);

    BigDecimal initKeyJdglDiffAnalysisPath(JdglDiffAnalysis jdglDiffAnalysis);

    BigDecimal initNotKeyJdglDiffAnalysisPath(JdglDiffAnalysis jdglDiffAnalysis);

    List<JdglDiffAnalysisPath> getJdglDiffAnalysisPathLazyList(JdglDiffAnalysisPath jdglDiffAnalysisPathParam);
}
