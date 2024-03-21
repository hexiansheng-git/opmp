package com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.mapper;

import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsBuildScheme;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fushudong
 * @date 2024-03-19 15:57:26
 * @remark
 */
public interface SgjsBuildSchemeMapper {

    SgjsBuildScheme getSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme);

    List<SgjsBuildScheme> getSgjsBuildSchemeList(SgjsBuildScheme sgjsBuildScheme);

    int insertSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme);

    int insertSgjsBuildSchemeList(@Param("sgjsBuildSchemeList") List<SgjsBuildScheme> sgjsBuildSchemeList);

    int updateSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme);

    int updateSgjsBuildSchemeList(@Param("sgjsBuildSchemeList") List<SgjsBuildScheme> sgjsBuildSchemeList);

    int deleteSgjsBuildScheme(SgjsBuildScheme sgjsBuildScheme);

    int deleteSgjsBuildSchemeByPks(@Param("sgjsBuildSchemePkList") List<Long> sgjsBuildSchemePkList);

    SgjsBuildScheme getMaxVersionData();
    SgjsBuildScheme getValidVersionData();
}
