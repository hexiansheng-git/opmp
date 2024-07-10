package com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.service;

import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.domain.SgjsPaperScoreRecord;

import java.util.List;


/**
 * @author fsd
 * @date 2024-07-10 16:38:37
 * @remark
 */
public interface ISgjsPaperScoreRecordService {

    SgjsPaperScoreRecord getSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord);

    List<SgjsPaperScoreRecord> getSgjsPaperScoreRecordList(SgjsPaperScoreRecord sgjsPaperScoreRecord);

    int insertSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord);

    int insertSgjsPaperScoreRecordList(List<SgjsPaperScoreRecord> sgjsPaperScoreRecordList);

    int updateSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord);

    int updateSgjsPaperScoreRecordList(List<SgjsPaperScoreRecord> sgjsPaperScoreRecordList);

    int deleteSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord);

    int deleteSgjsPaperScoreRecordByPks(List<Long> sgjsPaperScoreRecordPkList);
}
