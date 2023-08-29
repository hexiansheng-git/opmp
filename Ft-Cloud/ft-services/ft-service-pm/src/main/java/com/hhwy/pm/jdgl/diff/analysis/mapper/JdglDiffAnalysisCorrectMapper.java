package com.hhwy.pm.jdgl.diff.analysis.mapper;

import java.util.List;

import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisCorrect;
import org.apache.ibatis.annotations.Param;

/**
 * @author 陈锦豪
 * @date 2023-08-28 16:24:13
 * @remark
 */
public interface JdglDiffAnalysisCorrectMapper {

    JdglDiffAnalysisCorrect getJdglDiffAnalysisCorrect(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect);

    List<JdglDiffAnalysisCorrect> getJdglDiffAnalysisCorrectList(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect);

    int insertJdglDiffAnalysisCorrect(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect);

    int insertJdglDiffAnalysisCorrectList(@Param("jdglDiffAnalysisCorrectList") List<JdglDiffAnalysisCorrect> jdglDiffAnalysisCorrectList);

    int updateJdglDiffAnalysisCorrect(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect);

    int updateJdglDiffAnalysisCorrectList(@Param("jdglDiffAnalysisCorrectList") List<JdglDiffAnalysisCorrect> jdglDiffAnalysisCorrectList);

    int deleteJdglDiffAnalysisCorrect(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect);

    int deleteJdglDiffAnalysisCorrectByPks(@Param("jdglDiffAnalysisCorrectPkList") List<Long> jdglDiffAnalysisCorrectPkList);
}
