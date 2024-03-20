package com.hhwy.sp.buildSchemeManage.review.mapper;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReview;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark
 */
public interface SgjsBuildSchemeReviewMapper {

    SgjsBuildSchemeReview getSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    List<SgjsBuildSchemeReview> getSgjsBuildSchemeReviewList(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int insertSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int insertSgjsBuildSchemeReviewList(@Param("sgjsBuildSchemeReviewList") List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList);

    int updateSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int updateSgjsBuildSchemeReviewList(@Param("list") List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList);

    int deleteSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int deleteSgjsBuildSchemeReviewByPks(@Param("sgjsBuildSchemeReviewPkList") List<Long> sgjsBuildSchemeReviewPkList);
}
