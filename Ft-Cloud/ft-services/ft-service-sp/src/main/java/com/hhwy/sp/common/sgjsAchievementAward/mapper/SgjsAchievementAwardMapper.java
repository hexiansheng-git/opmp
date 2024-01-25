package com.hhwy.sp.common.sgjsAchievementAward.mapper;

import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author han
 * @date 2024-01-25 09:45:38
 * @remark
 */
public interface SgjsAchievementAwardMapper {

    SgjsAchievementAward getSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward);

    List<SgjsAchievementAward> getSgjsAchievementAwardList(SgjsAchievementAward sgjsAchievementAward);

    int insertSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward);

    int insertSgjsAchievementAwardList(@Param("sgjsAchievementAwardList") List<SgjsAchievementAward> sgjsAchievementAwardList);

    int updateSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward);

    int updateSgjsAchievementAwardList(@Param("list") List<SgjsAchievementAward> sgjsAchievementAwardList);

    int deleteSgjsAchievementAward(SgjsAchievementAward sgjsAchievementAward);

    int deleteSgjsAchievementAwardByPks(@Param("sgjsAchievementAwardPkList") List<Long> sgjsAchievementAwardPkList);
}
