package com.hhwy.pm.jdgl.diff.analysis.mapper;

import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.vo.DiffAnalysisQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 陈锦豪
 * @date 2023-08-28 15:06:24
 * @remark
 */
public interface JdglDiffAnalysisMapper {

    JdglDiffAnalysis getJdglDiffAnalysis(JdglDiffAnalysis jdglDiffAnalysis);

    List<JdglDiffAnalysis> getJdglDiffAnalysisList(JdglDiffAnalysis jdglDiffAnalysis);

    int insertJdglDiffAnalysis(JdglDiffAnalysis jdglDiffAnalysis);

    int insertJdglDiffAnalysisList(@Param("jdglDiffAnalysisList") List<JdglDiffAnalysis> jdglDiffAnalysisList);

    int updateJdglDiffAnalysis(JdglDiffAnalysis jdglDiffAnalysis);

    int updateJdglDiffAnalysisList(@Param("jdglDiffAnalysisList") List<JdglDiffAnalysis> jdglDiffAnalysisList);

    int deleteJdglDiffAnalysis(JdglDiffAnalysis jdglDiffAnalysis);

    int deleteJdglDiffAnalysisByPks(@Param("jdglDiffAnalysisPkList") List<Long> jdglDiffAnalysisPkList);

    List<JdglDiffAnalysis> gmList(DiffAnalysisQueryVo queryVo);
}
