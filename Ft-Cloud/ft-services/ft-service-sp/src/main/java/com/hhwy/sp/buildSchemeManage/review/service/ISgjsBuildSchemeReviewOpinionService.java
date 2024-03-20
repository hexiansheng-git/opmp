package com.hhwy.sp.buildSchemeManage.review.service;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewOpinion;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:39
 * @remark
 */
public interface ISgjsBuildSchemeReviewOpinionService {

    SgjsBuildSchemeReviewOpinion getSgjsBuildSchemeReviewOpinion(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion);

    List<SgjsBuildSchemeReviewOpinion> getSgjsBuildSchemeReviewOpinionList(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion);

    int insertSgjsBuildSchemeReviewOpinion(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion);

    int insertSgjsBuildSchemeReviewOpinionList(List<SgjsBuildSchemeReviewOpinion> sgjsBuildSchemeReviewOpinionList);

    int updateSgjsBuildSchemeReviewOpinion(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion);

    int updateSgjsBuildSchemeReviewOpinionList(List<SgjsBuildSchemeReviewOpinion> sgjsBuildSchemeReviewOpinionList);

    int deleteSgjsBuildSchemeReviewOpinion(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion);

    int deleteSgjsBuildSchemeReviewOpinionByPks(List<Long> sgjsBuildSchemeReviewOpinionPkList);
}
