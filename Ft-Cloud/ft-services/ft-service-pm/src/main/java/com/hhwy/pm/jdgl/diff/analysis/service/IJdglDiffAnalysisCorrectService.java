package com.hhwy.pm.jdgl.diff.analysis.service;

import java.util.List;
import java.util.Map;

import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisCorrect;

/**
 * @author 陈锦豪
 * @date 2023-08-28 16:24:13
 * @remark
 */
public interface IJdglDiffAnalysisCorrectService {

    JdglDiffAnalysisCorrect getJdglDiffAnalysisCorrect(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect);

    List<JdglDiffAnalysisCorrect> getJdglDiffAnalysisCorrectList(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect);

    Map<String, List<JdglDiffAnalysisCorrect>> getJdglDiffAnalysisCorrectMapList(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect);

    int insertJdglDiffAnalysisCorrect(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect);

    int insertJdglDiffAnalysisCorrectList(List<JdglDiffAnalysisCorrect> jdglDiffAnalysisCorrectList);

    int updateJdglDiffAnalysisCorrect(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect);

    int updateJdglDiffAnalysisCorrectList(List<JdglDiffAnalysisCorrect> jdglDiffAnalysisCorrectList);

    int deleteJdglDiffAnalysisCorrect(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect);

    int deleteJdglDiffAnalysisCorrectByPks(List<Long> jdglDiffAnalysisCorrectPkList);

    Map<String, List<JdglDiffAnalysisCorrect>> getInitDiffAnalysisCorrect(JdglDiffAnalysisCorrect jdglDiffAnalysisCorrectParam);
}
