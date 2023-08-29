package com.hhwy.pm.jdgl.diff.analysis.mapper;

import java.util.List;

import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisSv;
import org.apache.ibatis.annotations.Param;

/**
 * @author 陈锦豪
 * @date 2023-08-28 16:24:27
 * @remark
 */
public interface JdglDiffAnalysisSvMapper {

    JdglDiffAnalysisSv getJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv);

    List<JdglDiffAnalysisSv> getJdglDiffAnalysisSvList(JdglDiffAnalysisSv jdglDiffAnalysisSv);

    int insertJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv);

    int insertJdglDiffAnalysisSvList(@Param("jdglDiffAnalysisSvList") List<JdglDiffAnalysisSv> jdglDiffAnalysisSvList);

    int updateJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv);

    int updateJdglDiffAnalysisSvList(@Param("jdglDiffAnalysisSvList") List<JdglDiffAnalysisSv> jdglDiffAnalysisSvList);

    int deleteJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv);

    int deleteJdglDiffAnalysisSvByPks(@Param("jdglDiffAnalysisSvPkList") List<Long> jdglDiffAnalysisSvPkList);
}
