package com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreStandard.service;


import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreStandard.domain.SgjsPaperScoreStandard;

import java.util.List;

/**
 * @author fsd
 * @date 2024-07-10 16:38:46
 * @remark
 */
public interface ISgjsPaperScoreStandardService {

    SgjsPaperScoreStandard getSgjsPaperScoreStandard(SgjsPaperScoreStandard sgjsPaperScoreStandard);

    List<SgjsPaperScoreStandard> getSgjsPaperScoreStandardList(SgjsPaperScoreStandard sgjsPaperScoreStandard);

    int insertSgjsPaperScoreStandard(SgjsPaperScoreStandard sgjsPaperScoreStandard);

    int insertSgjsPaperScoreStandardList(List<SgjsPaperScoreStandard> sgjsPaperScoreStandardList);

    int updateSgjsPaperScoreStandard(SgjsPaperScoreStandard sgjsPaperScoreStandard);

    int updateSgjsPaperScoreStandardList(List<SgjsPaperScoreStandard> sgjsPaperScoreStandardList);

    int deleteSgjsPaperScoreStandard(SgjsPaperScoreStandard sgjsPaperScoreStandard);

    int deleteSgjsPaperScoreStandardByPks(List<Long> sgjsPaperScoreStandardPkList);
}
