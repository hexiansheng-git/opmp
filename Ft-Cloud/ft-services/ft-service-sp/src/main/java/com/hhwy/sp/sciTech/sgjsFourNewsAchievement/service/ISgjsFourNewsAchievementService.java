package com.hhwy.sp.sciTech.sgjsFourNewsAchievement.service;

import java.util.List;

import com.hhwy.sp.sciTech.sgjsFourNewsAchievement.domain.SgjsFourNewsAchievement;

/**
 * @author cjh
 * @date 2024-01-25 10:10:50
 * @remark
 */
public interface ISgjsFourNewsAchievementService {

    SgjsFourNewsAchievement getSgjsFourNewsAchievement(SgjsFourNewsAchievement sgjsFourNewsAchievement);

    List<SgjsFourNewsAchievement> getSgjsFourNewsAchievementList(SgjsFourNewsAchievement sgjsFourNewsAchievement);

    int insertSgjsFourNewsAchievement(SgjsFourNewsAchievement sgjsFourNewsAchievement);

    int insertSgjsFourNewsAchievementList(List<SgjsFourNewsAchievement> sgjsFourNewsAchievementList);

    SgjsFourNewsAchievement updateSgjsFourNewsAchievement(SgjsFourNewsAchievement sgjsFourNewsAchievement);

    int updateSgjsFourNewsAchievementList(List<SgjsFourNewsAchievement> sgjsFourNewsAchievementList);

    int deleteSgjsFourNewsAchievement(SgjsFourNewsAchievement sgjsFourNewsAchievement);

    int deleteSgjsFourNewsAchievementByPks(List<Long> sgjsFourNewsAchievementPkList);
}
