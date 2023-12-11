package com.hhwy.sp.experiment.sgjsExperimentRecord.service;

import java.util.List;
import com.hhwy.sp.experiment.sgjsExperimentRecord.domain.SgjsExperimentRecord;

/**
 * @author lcf--试验设备进场记录
 * @date 2023-12-11 15:03:30
 * @remark
 */
public interface ISgjsExperimentRecordService {

    SgjsExperimentRecord getSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord);

    List<SgjsExperimentRecord> getSgjsExperimentRecordList(SgjsExperimentRecord sgjsExperimentRecord);

    int insertSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord);

    int insertSgjsExperimentRecordList(List<SgjsExperimentRecord> sgjsExperimentRecordList);

    int updateSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord);

    int updateSgjsExperimentRecordList(List<SgjsExperimentRecord> sgjsExperimentRecordList);

    int deleteSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord);

    int deleteSgjsExperimentRecordByPks(List<Long> sgjsExperimentRecordPkList);
}
