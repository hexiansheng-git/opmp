package com.hhwy.sd.achievementReview.service;

import com.hhwy.sd.achievementReview.domain.KcsjAchievement;
import com.hhwy.sd.achievementReview.domain.vo.AchievementQueryVo;
import com.hhwy.sd.achievementReview.domain.vo.AchievementVo;

import java.util.List;


/**
 * @author han
 * @date 2024-02-05 09:04:08
 * @remark
 */
public interface IKcsjAchievementService {

    KcsjAchievement getKcsjAchievement(KcsjAchievement kcsjAchievement);

    List<KcsjAchievement> getKcsjAchievementList(AchievementQueryVo queryVo);

    int insertKcsjAchievement(KcsjAchievement kcsjAchievement);

    int insertKcsjAchievementList(List<KcsjAchievement> kcsjAchievementList);

    int updateKcsjAchievement(KcsjAchievement kcsjAchievement);

    int updateKcsjAchievementList(List<KcsjAchievement> kcsjAchievementList);

    int deleteKcsjAchievement(KcsjAchievement kcsjAchievement);

    int deleteKcsjAchievementByPks(List<Long> kcsjAchievementPkList);

    void save(AchievementVo achievementVo);

    List<KcsjAchievement> getListByIds(List<Long> ids);
}
