package com.hhwy.sd.achievementReview.service;

import com.hhwy.sd.achievementReview.domain.KcsjAchievement;

import java.util.List;


/**
 * @author han
 * @date 2024-02-05 09:04:08
 * @remark
 */
public interface IKcsjAchievementService {

    KcsjAchievement getKcsjAchievement(KcsjAchievement kcsjAchievement);

    List<KcsjAchievement> getKcsjAchievementList(KcsjAchievement kcsjAchievement);

    int insertKcsjAchievement(KcsjAchievement kcsjAchievement);

    int insertKcsjAchievementList(List<KcsjAchievement> kcsjAchievementList);

    int updateKcsjAchievement(KcsjAchievement kcsjAchievement);

    int updateKcsjAchievementList(List<KcsjAchievement> kcsjAchievementList);

    int deleteKcsjAchievement(KcsjAchievement kcsjAchievement);

    int deleteKcsjAchievementByPks(List<Long> kcsjAchievementPkList);
}
