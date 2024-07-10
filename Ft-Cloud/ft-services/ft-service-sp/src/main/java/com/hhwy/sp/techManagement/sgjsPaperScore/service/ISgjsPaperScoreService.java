package com.hhwy.sp.techManagement.sgjsPaperScore.service;

import java.util.List;
import com.hhwy.sp.techManagement.sgjsPaperScore.domain.SgjsPaperScore;

/**
 * @author fsd
 * @date 2024-07-10 16:38:08
 * @remark 
 */
public interface ISgjsPaperScoreService {
                                                                                                                                                                                                                                                                                                                                                                            
    SgjsPaperScore getSgjsPaperScore(SgjsPaperScore sgjsPaperScore);

    List<SgjsPaperScore> getSgjsPaperScoreList(SgjsPaperScore sgjsPaperScore);

    int insertSgjsPaperScore(SgjsPaperScore sgjsPaperScore);

    int insertSgjsPaperScoreList(List<SgjsPaperScore> sgjsPaperScoreList);

    int updateSgjsPaperScore(SgjsPaperScore sgjsPaperScore);

            int updateSgjsPaperScoreList(List<SgjsPaperScore> sgjsPaperScoreList);
    
    int deleteSgjsPaperScore(SgjsPaperScore sgjsPaperScore);

            int deleteSgjsPaperScoreByPks(List<Long> sgjsPaperScorePkList);
    }
