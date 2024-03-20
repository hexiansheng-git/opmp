package com.hhwy.sp.buildSchemeManage.review.service;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeStaffOpinion;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:59
 * @remark
 */
public interface ISgjsBuildSchemeStaffOpinionService {

    SgjsBuildSchemeStaffOpinion getSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    List<SgjsBuildSchemeStaffOpinion> getSgjsBuildSchemeStaffOpinionList(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    int insertSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    int insertSgjsBuildSchemeStaffOpinionList(List<SgjsBuildSchemeStaffOpinion> sgjsBuildSchemeStaffOpinionList);

    int updateSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    int updateSgjsBuildSchemeStaffOpinionList(List<SgjsBuildSchemeStaffOpinion> sgjsBuildSchemeStaffOpinionList);

    int deleteSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    int deleteSgjsBuildSchemeStaffOpinionByPks(List<Long> sgjsBuildSchemeStaffOpinionPkList);
}
