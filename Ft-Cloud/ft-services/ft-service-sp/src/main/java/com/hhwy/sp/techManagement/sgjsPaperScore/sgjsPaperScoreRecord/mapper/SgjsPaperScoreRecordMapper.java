package com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.mapper;

import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.domain.SgjsPaperScoreRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fsd
 * @date 2024-07-10 16:38:37
 * @remark
 */
public interface SgjsPaperScoreRecordMapper {

    SgjsPaperScoreRecord getSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord);

    List<SgjsPaperScoreRecord> getSgjsPaperScoreRecordList(SgjsPaperScoreRecord sgjsPaperScoreRecord);

    int insertSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord);

    int insertSgjsPaperScoreRecordList(@Param("sgjsPaperScoreRecordList") List<SgjsPaperScoreRecord> sgjsPaperScoreRecordList);

    int updateSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord);

    int updateSgjsPaperScoreRecordList(@Param("sgjsPaperScoreRecordList") List<SgjsPaperScoreRecord> sgjsPaperScoreRecordList);

    int deleteSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord);

    int deleteSgjsPaperScoreRecordByPks(@Param("sgjsPaperScoreRecordPkList") List<Long> sgjsPaperScoreRecordPkList);

    List<SgjsPaperScoreRecord> getListByForeginId(@Param("idList") List<Long> idList);
}
