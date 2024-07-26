package com.hhwy.sp.techManagement.sgjsPaperPublish.sgjsPaperPublishSpecialistReview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.techManagement.sgjsPaperPublish.sgjsPaperPublishSpecialistReview.domain.SgjsPaperPublishSpecialistReview;

/**
 * @author fsd
 * @date 2024-07-24 14:15:53
 * @remark
 */
public interface SgjsPaperPublishSpecialistReviewMapper {

    SgjsPaperPublishSpecialistReview getSgjsPaperPublishSpecialistReview(SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview);

    List<SgjsPaperPublishSpecialistReview> getSgjsPaperPublishSpecialistReviewList(SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview);

    int insertSgjsPaperPublishSpecialistReview(SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview);

    int insertSgjsPaperPublishSpecialistReviewList(@Param("sgjsPaperPublishSpecialistReviewList") List<SgjsPaperPublishSpecialistReview> sgjsPaperPublishSpecialistReviewList);

    int updateSgjsPaperPublishSpecialistReview(SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview);

    int updateSgjsPaperPublishSpecialistReviewList(@Param("sgjsPaperPublishSpecialistReviewList") List<SgjsPaperPublishSpecialistReview> sgjsPaperPublishSpecialistReviewList);

    int deleteSgjsPaperPublishSpecialistReview(SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview);

    int deleteSgjsPaperPublishSpecialistReviewByPks(@Param("sgjsPaperPublishSpecialistReviewPkList") List<Long> sgjsPaperPublishSpecialistReviewPkList);
}
