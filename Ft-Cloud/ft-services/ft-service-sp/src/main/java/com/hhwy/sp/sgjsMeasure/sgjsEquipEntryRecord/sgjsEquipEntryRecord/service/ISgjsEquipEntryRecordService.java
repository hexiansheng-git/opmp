package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.service;

import java.util.List;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.domain.SgjsEquipEntryRecord;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:47:00
 * @remark
 */
public interface ISgjsEquipEntryRecordService {

    SgjsEquipEntryRecord getSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    List<SgjsEquipEntryRecord> getSgjsEquipEntryRecordList(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    int insertSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    int insertSgjsEquipEntryRecordList(List<SgjsEquipEntryRecord> sgjsEquipEntryRecordList);

    int updateSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    int updateSgjsEquipEntryRecordList(List<SgjsEquipEntryRecord> sgjsEquipEntryRecordList);

    int deleteSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    int deleteSgjsEquipEntryRecordByPks(List<Long> sgjsEquipEntryRecordPkList);
}
