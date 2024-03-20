package com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.service;

import java.util.List;

import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.domain.SgjsBuildSchemeExpertSuggest;

/**
 * @author fsd
 * @date 2024-03-20 18:18:03
 * @remark
 */
public interface ISgjsBuildSchemeExpertSuggestService {

    SgjsBuildSchemeExpertSuggest getSgjsBuildSchemeExpertSuggest(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest);

    List<SgjsBuildSchemeExpertSuggest> getSgjsBuildSchemeExpertSuggestList(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest);

    int insertSgjsBuildSchemeExpertSuggest(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest);

    int insertSgjsBuildSchemeExpertSuggestList(List<SgjsBuildSchemeExpertSuggest> sgjsBuildSchemeExpertSuggestList);

    int updateSgjsBuildSchemeExpertSuggest(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest);

    int updateSgjsBuildSchemeExpertSuggestList(List<SgjsBuildSchemeExpertSuggest> sgjsBuildSchemeExpertSuggestList);

    int deleteSgjsBuildSchemeExpertSuggest(SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest);

    int deleteSgjsBuildSchemeExpertSuggestByPks(List<Long> sgjsBuildSchemeExpertSuggestPkList);
}
