package com.hhwy.sp.buildSchemeManage.review.service;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReview;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark
 */
public interface ISgjsBuildSchemeReviewService {

    SgjsBuildSchemeReview getSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    List<SgjsBuildSchemeReview> getSgjsBuildSchemeReviewList(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int insertSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int insertSgjsBuildSchemeReviewList(List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList);

    int updateSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int updateSgjsBuildSchemeReviewList(List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList);

    int deleteSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int deleteSgjsBuildSchemeReviewByPks(List<Long> sgjsBuildSchemeReviewPkList);
}
