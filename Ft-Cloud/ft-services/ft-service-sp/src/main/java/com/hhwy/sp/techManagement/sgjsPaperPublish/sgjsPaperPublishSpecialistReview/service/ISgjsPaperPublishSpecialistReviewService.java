package com.hhwy.sp.techManagement.sgjsPaperPublish.sgjsPaperPublishSpecialistReview.service;

import java.util.List;

import com.hhwy.sp.techManagement.sgjsPaperPublish.sgjsPaperPublishSpecialistReview.domain.SgjsPaperPublishSpecialistReview;

/**
 * @author fsd
 * @date 2024-07-24 14:15:53
 * @remark
 */
public interface ISgjsPaperPublishSpecialistReviewService {

    SgjsPaperPublishSpecialistReview getSgjsPaperPublishSpecialistReview(SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview);

    List<SgjsPaperPublishSpecialistReview> getSgjsPaperPublishSpecialistReviewList(SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview);

    int insertSgjsPaperPublishSpecialistReview(SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview);

    int insertSgjsPaperPublishSpecialistReviewList(List<SgjsPaperPublishSpecialistReview> sgjsPaperPublishSpecialistReviewList);

    int updateSgjsPaperPublishSpecialistReview(SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview);

    int updateSgjsPaperPublishSpecialistReviewList(List<SgjsPaperPublishSpecialistReview> sgjsPaperPublishSpecialistReviewList);

    int deleteSgjsPaperPublishSpecialistReview(SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview);

    int deleteSgjsPaperPublishSpecialistReviewByPks(List<Long> sgjsPaperPublishSpecialistReviewPkList);
}
