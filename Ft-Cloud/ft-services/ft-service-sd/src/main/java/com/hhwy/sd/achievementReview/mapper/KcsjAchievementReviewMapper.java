package com.hhwy.sd.achievementReview.mapper;

import com.hhwy.sd.achievementReview.domain.KcsjAchievementReview;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2024-02-05 09:04:12
 * @remark
 */
@Repository
public interface KcsjAchievementReviewMapper {

    KcsjAchievementReview getKcsjAchievementReview(KcsjAchievementReview kcsjAchievementReview);

    List<KcsjAchievementReview> getKcsjAchievementReviewList(KcsjAchievementReview kcsjAchievementReview);

    int insertKcsjAchievementReview(KcsjAchievementReview kcsjAchievementReview);

    int insertKcsjAchievementReviewList(@Param("kcsjAchievementReviewList") List<KcsjAchievementReview> kcsjAchievementReviewList);

    int updateKcsjAchievementReview(KcsjAchievementReview kcsjAchievementReview);

    int updateKcsjAchievementReviewList(@Param("list") List<KcsjAchievementReview> kcsjAchievementReviewList);

    int deleteKcsjAchievementReview(KcsjAchievementReview kcsjAchievementReview);

    int deleteKcsjAchievementReviewByPks(@Param("kcsjAchievementReviewPkList") List<Long> kcsjAchievementReviewPkList);

    KcsjAchievementReview getKcsjAchievementReviewById(@Param("id") Long id);

    void deleteById(@Param("id") Long id);
}
