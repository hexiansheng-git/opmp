package com.hhwy.sp.techManagement.sgjsPaperScore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.techManagement.sgjsPaperScore.domain.SgjsPaperScore;

/**
 * @author fsd
 * @date 2024-07-10 16:38:08
 * @remark
 */
public interface SgjsPaperScoreMapper {

    SgjsPaperScore getSgjsPaperScore(SgjsPaperScore sgjsPaperScore);

    List<SgjsPaperScore> getSgjsPaperScoreList(SgjsPaperScore sgjsPaperScore);

    int insertSgjsPaperScore(SgjsPaperScore sgjsPaperScore);

    int insertSgjsPaperScoreList(@Param("sgjsPaperScoreList") List<SgjsPaperScore> sgjsPaperScoreList);

    int updateSgjsPaperScore(SgjsPaperScore sgjsPaperScore);

    int updateSgjsPaperScoreList(@Param("sgjsPaperScoreList") List<SgjsPaperScore> sgjsPaperScoreList);

    int deleteSgjsPaperScore(SgjsPaperScore sgjsPaperScore);

    int deleteSgjsPaperScoreByPks(@Param("sgjsPaperScorePkList") List<Long> sgjsPaperScorePkList);

    List<SgjsPaperScore> getListByIds(@Param("ids") List<Long> ids);

    void updateByIds(@Param("ids") List<Long> ids, @Param("taskStatus") String taskStatus);
}
