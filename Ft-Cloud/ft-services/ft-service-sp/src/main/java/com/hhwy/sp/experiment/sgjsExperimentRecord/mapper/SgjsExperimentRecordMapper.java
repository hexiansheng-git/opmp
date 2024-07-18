package com.hhwy.sp.experiment.sgjsExperimentRecord.mapper;

import com.hhwy.sp.experiment.sgjsExperimentRecord.domain.SgjsExperimentRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lcf--试验设备进场记录
 * @date 2023-12-11 15:03:30
 * @remark
 */
public interface SgjsExperimentRecordMapper {

    SgjsExperimentRecord getSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord);

    List<SgjsExperimentRecord> getSgjsExperimentRecordList(SgjsExperimentRecord sgjsExperimentRecord);

    int insertSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord);

    int insertSgjsExperimentRecordList(@Param("sgjsExperimentRecordList") List<SgjsExperimentRecord> sgjsExperimentRecordList);

    int updateSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord);

    int updateSgjsExperimentRecordList(@Param("list") List<SgjsExperimentRecord> sgjsExperimentRecordList);

    int deleteSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord);

    int deleteSgjsExperimentRecordByPks(@Param("sgjsExperimentRecordPkList") List<Long> sgjsExperimentRecordPkList);

    /**
     * 批量修改
     *
     * @param eList
     * @return
     */
    int bathUpdateByList(@Param("list") List<SgjsExperimentRecord> eList);

    int updateByDelIdList(@Param("delIdList") List<Long> delIdList);
}
