package com.hhwy.sp.common.sgjsAchievementAward.service;

import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;

import java.util.List;

/**
 * @author han
 * @date 2024-01-25 09:45:38
 * @remark
 */
public interface ISgjsAchievementAwardService {

    SgjsAchievementAward getSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward);

    List<SgjsAchievementAward> getSgjsAchievementAwardList(SgjsAchievementAward sgjsAchievementAward);

    int insertSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward);

    int insertSgjsAchievementAwardList(List<SgjsAchievementAward> sgjsAchievementAwardList);

    int updateSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward);

    int updateSgjsAchievementAwardList(List<SgjsAchievementAward> sgjsAchievementAwardList);

    int deleteSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward);

    int deleteSgjsAchievementAwardByPks(List<Long> sgjsAchievementAwardPkList);
}
