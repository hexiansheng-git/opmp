package com.hhwy.sp.buildScheme.sgjsBuildSchemeList.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.buildScheme.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;

/**
 * @author fushudong
 * @date 2024-03-19 15:57:37
 * @remark
 */
public interface SgjsBuildSchemeListMapper {

    SgjsBuildSchemeList getSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList);

    List<SgjsBuildSchemeList> getSgjsBuildSchemeListList(SgjsBuildSchemeList sgjsBuildSchemeList);

    int insertSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList);

    int insertSgjsBuildSchemeListList(@Param("sgjsBuildSchemeListList") List<SgjsBuildSchemeList> sgjsBuildSchemeListList);

    int updateSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList);

    int updateSgjsBuildSchemeListList(@Param("sgjsBuildSchemeListList") List<SgjsBuildSchemeList> sgjsBuildSchemeListList);

    int deleteSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList);

    int deleteSgjsBuildSchemeListByPks(@Param("sgjsBuildSchemeListPkList") List<Long> sgjsBuildSchemeListPkList);
}
