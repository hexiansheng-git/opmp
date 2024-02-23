package com.hhwy.sd.achievementReview.mapper;

import com.hhwy.sd.achievementReview.domain.KcsjAchievement;
import com.hhwy.sd.achievementReview.domain.vo.AchievementQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2024-02-05 09:04:08
 * @remark
 */
@Repository
public interface KcsjAchievementMapper {

    KcsjAchievement getKcsjAchievement(KcsjAchievement kcsjAchievement);

    List<KcsjAchievement> getKcsjAchievementList(AchievementQueryVo queryVo);

    int insertKcsjAchievement(KcsjAchievement kcsjAchievement);

    int insertKcsjAchievementList(@Param("kcsjAchievementList") List<KcsjAchievement> kcsjAchievementList);

    int updateKcsjAchievement(KcsjAchievement kcsjAchievement);

    int updateKcsjAchievementList(@Param("list") List<KcsjAchievement> kcsjAchievementList);

    int deleteKcsjAchievement(KcsjAchievement kcsjAchievement);

    int deleteKcsjAchievementByPks(@Param("kcsjAchievementPkList") List<Long> kcsjAchievementPkList);

    List<KcsjAchievement> getListByIds(@Param("ids") List<Long> ids);

    List<KcsjAchievement> getListByForeignId(@Param("foreignId") Long foreignId);

    void cleanForeignIdByForeignId(@Param("foreignId") Long foreignId);

    void relevancy(@Param("ids") List<Long> ids,@Param("foreignId") Long foreignId);

    void updateAchievementStatusByForeignId(@Param("foreignId") Long foreignId,@Param("achievementStatus") String achievementStatus);

    void updateReviewExpertByForeignId(@Param("foreignId") Long foreignId,@Param("reviewExpert") String reviewExpert);
}
