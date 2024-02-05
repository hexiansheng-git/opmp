package com.hhwy.sd.achievementReview.service;

import com.hhwy.sd.achievementReview.domain.KcsjAchievementReview;

import java.util.List;


/**
 * @author han
 * @date 2024-02-05 09:04:12
 * @remark
 */
public interface IKcsjAchievementReviewService {

    KcsjAchievementReview getKcsjAchievementReview(KcsjAchievementReview kcsjAchievementReview);

    List<KcsjAchievementReview> getKcsjAchievementReviewList(KcsjAchievementReview kcsjAchievementReview);

    int insertKcsjAchievementReview(KcsjAchievementReview kcsjAchievementReview);

    int insertKcsjAchievementReviewList(List<KcsjAchievementReview> kcsjAchievementReviewList);

    int updateKcsjAchievementReview(KcsjAchievementReview kcsjAchievementReview);

    int updateKcsjAchievementReviewList(List<KcsjAchievementReview> kcsjAchievementReviewList);

    int deleteKcsjAchievementReview(KcsjAchievementReview kcsjAchievementReview);

    int deleteKcsjAchievementReviewByPks(List<Long> kcsjAchievementReviewPkList);
}
