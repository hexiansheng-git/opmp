package com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.domain.SgjsBuildSchemeExpertSuggest;

/**
 * @author fsd
 * @date 2024-03-20 18:18:03
 * @remark
 */
public interface SgjsBuildSchemeExpertSuggestMapper {

    SgjsBuildSchemeExpertSuggest getSgjsBuildSchemeExpertSuggest(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest);

    List<SgjsBuildSchemeExpertSuggest> getSgjsBuildSchemeExpertSuggestList(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest);

    int insertSgjsBuildSchemeExpertSuggest(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest);

    int insertSgjsBuildSchemeExpertSuggestList(@Param("sgjsBuildSchemeExpertSuggestList") List<SgjsBuildSchemeExpertSuggest> sgjsBuildSchemeExpertSuggestList);

    int updateSgjsBuildSchemeExpertSuggest(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest);

    int updateSgjsBuildSchemeExpertSuggestList(@Param("sgjsBuildSchemeExpertSuggestList") List<SgjsBuildSchemeExpertSuggest> sgjsBuildSchemeExpertSuggestList);

    int deleteSgjsBuildSchemeExpertSuggest(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest);

    int deleteSgjsBuildSchemeExpertSuggestByPks(@Param("sgjsBuildSchemeExpertSuggestPkList") List<Long> sgjsBuildSchemeExpertSuggestPkList);

    List<SgjsBuildSchemeExpertSuggest> getGroupList(Long foreignId);

    List<SgjsBuildSchemeExpertSuggest> getListByforeignList(@Param("foreignIds") Set<Long> foreignIds);
}
