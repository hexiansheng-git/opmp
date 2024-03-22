package com.hhwy.sp.buildSchemeManage.review.mapper;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewOpinion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:39
 * @remark
 */
@Repository
public interface SgjsBuildSchemeReviewOpinionMapper {

    SgjsBuildSchemeReviewOpinion getSgjsBuildSchemeReviewOpinion(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion);

    List<SgjsBuildSchemeReviewOpinion> getSgjsBuildSchemeReviewOpinionList(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion);

    List<SgjsBuildSchemeReviewOpinion> getListByReviewId(@Param("reviewId") Long reviewId);

    int insertSgjsBuildSchemeReviewOpinion(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion);

    int insertSgjsBuildSchemeReviewOpinionList(@Param("sgjsBuildSchemeReviewOpinionList") List<SgjsBuildSchemeReviewOpinion> sgjsBuildSchemeReviewOpinionList);

    int updateSgjsBuildSchemeReviewOpinion(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion);

    int updateSgjsBuildSchemeReviewOpinionList(@Param("list") List<SgjsBuildSchemeReviewOpinion> sgjsBuildSchemeReviewOpinionList);

    int deleteSgjsBuildSchemeReviewOpinion(SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion);

    void deleteByReviewId(@Param("reviewId") Long reviewId);

    int deleteSgjsBuildSchemeReviewOpinionByPks(@Param("sgjsBuildSchemeReviewOpinionPkList") List<Long> sgjsBuildSchemeReviewOpinionPkList);
}
