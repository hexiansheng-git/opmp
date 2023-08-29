package com.hhwy.pm.jdgl.diff.analysis.mapper;

import java.util.List;

import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisPath;
import org.apache.ibatis.annotations.Param;

/**
 * @author 陈锦豪
 * @date 2023-08-28 16:24:21
 * @remark
 */
public interface JdglDiffAnalysisPathMapper {

    JdglDiffAnalysisPath getJdglDiffAnalysisPath(JdglDiffAnalysisPath jdglDiffAnalysisPath);

    List<JdglDiffAnalysisPath> getJdglDiffAnalysisPathList(JdglDiffAnalysisPath jdglDiffAnalysisPath);

    int insertJdglDiffAnalysisPath(JdglDiffAnalysisPath jdglDiffAnalysisPath);

    int insertJdglDiffAnalysisPathList(@Param("jdglDiffAnalysisPathList") List<JdglDiffAnalysisPath> jdglDiffAnalysisPathList);

    int updateJdglDiffAnalysisPath(JdglDiffAnalysisPath jdglDiffAnalysisPath);

    int updateJdglDiffAnalysisPathList(@Param("jdglDiffAnalysisPathList") List<JdglDiffAnalysisPath> jdglDiffAnalysisPathList);

    int deleteJdglDiffAnalysisPath(JdglDiffAnalysisPath jdglDiffAnalysisPath);

    int deleteJdglDiffAnalysisPathByPks(@Param("jdglDiffAnalysisPathPkList") List<Long> jdglDiffAnalysisPathPkList);
}
