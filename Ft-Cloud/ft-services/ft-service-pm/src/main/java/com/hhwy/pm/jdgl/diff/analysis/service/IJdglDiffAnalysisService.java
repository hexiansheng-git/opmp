package com.hhwy.pm.jdgl.diff.analysis.service;

import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.vo.DiffAnalysisQueryVo;

import java.util.List;

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

    /**
     * 生成差异化数据
     */
    void initDiffAnalysis();

    List<JdglDiffAnalysis> gmList(DiffAnalysisQueryVo queryVo);
}
