package com.hhwy.sp.sciTech.sgjsFourNewsAchievement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.sciTech.sgjsFourNewsAchievement.domain.SgjsFourNewsAchievement;

/**
 * @author cjh
 * @date 2024-01-25 10:10:50
 * @remark
 */
public interface SgjsFourNewsAchievementMapper {

    SgjsFourNewsAchievement getSgjsFourNewsAchievement(SgjsFourNewsAchievement sgjsFourNewsAchievement);

    List<SgjsFourNewsAchievement> getSgjsFourNewsAchievementList(SgjsFourNewsAchievement sgjsFourNewsAchievement);

    int insertSgjsFourNewsAchievement(SgjsFourNewsAchievement sgjsFourNewsAchievement);

    int insertSgjsFourNewsAchievementList(@Param("sgjsFourNewsAchievementList") List<SgjsFourNewsAchievement> sgjsFourNewsAchievementList);

    int updateSgjsFourNewsAchievement(SgjsFourNewsAchievement sgjsFourNewsAchievement);

    int updateSgjsFourNewsAchievementList(@Param("list") List<SgjsFourNewsAchievement> sgjsFourNewsAchievementList);

    int deleteSgjsFourNewsAchievement(SgjsFourNewsAchievement sgjsFourNewsAchievement);

    int deleteSgjsFourNewsAchievementByPks(@Param("sgjsFourNewsAchievementPkList") List<Long> sgjsFourNewsAchievementPkList);

    List<SgjsFourNewsAchievement> getSgjsFourNewsAchievementList4Ids(@Param("ids") List<Long> ids);
}
