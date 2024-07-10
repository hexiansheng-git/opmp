package com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreStandard.mapper;

import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreStandard.domain.SgjsPaperScoreStandard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fsd
 * @date 2024-07-10 16:38:46
 * @remark
 */
public interface SgjsPaperScoreStandardMapper {

    SgjsPaperScoreStandard getSgjsPaperScoreStandard(SgjsPaperScoreStandard sgjsPaperScoreStandard);

    List<SgjsPaperScoreStandard> getSgjsPaperScoreStandardList(SgjsPaperScoreStandard sgjsPaperScoreStandard);

    int insertSgjsPaperScoreStandard(SgjsPaperScoreStandard sgjsPaperScoreStandard);

    int insertSgjsPaperScoreStandardList(@Param("sgjsPaperScoreStandardList") List<SgjsPaperScoreStandard> sgjsPaperScoreStandardList);

    int updateSgjsPaperScoreStandard(SgjsPaperScoreStandard sgjsPaperScoreStandard);

    int updateSgjsPaperScoreStandardList(@Param("sgjsPaperScoreStandardList") List<SgjsPaperScoreStandard> sgjsPaperScoreStandardList);

    int deleteSgjsPaperScoreStandard(SgjsPaperScoreStandard sgjsPaperScoreStandard);

    int deleteSgjsPaperScoreStandardByPks(@Param("sgjsPaperScoreStandardPkList") List<Long> sgjsPaperScoreStandardPkList);
}
