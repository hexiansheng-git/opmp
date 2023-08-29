package com.hhwy.pm.jdgl.diff.analysis.service;

import java.util.List;

import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisSv;

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
}
