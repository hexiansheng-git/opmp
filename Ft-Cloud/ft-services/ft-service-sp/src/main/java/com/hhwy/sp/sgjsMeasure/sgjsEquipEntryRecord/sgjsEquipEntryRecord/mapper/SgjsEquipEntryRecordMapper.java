package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.domain.SgjsEquipEntryRecord;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:47:00
 * @remark
 */
public interface SgjsEquipEntryRecordMapper {

    SgjsEquipEntryRecord getSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    List<SgjsEquipEntryRecord> getSgjsEquipEntryRecordList(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    int insertSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    int insertSgjsEquipEntryRecordList(@Param("sgjsEquipEntryRecordList") List<SgjsEquipEntryRecord> sgjsEquipEntryRecordList);

    int updateSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    int updateSgjsEquipEntryRecordList(@Param("sgjsEquipEntryRecordList") List<SgjsEquipEntryRecord> sgjsEquipEntryRecordList);

    int deleteSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    int deleteSgjsEquipEntryRecordByPks(@Param("sgjsEquipEntryRecordPkList") List<Long> sgjsEquipEntryRecordPkList);

    /**
     * 批量修改
     *
     * @param equipList
     * @return
     */
    int bathUpdateByList(List<SgjsEquipEntryRecord> equipList);
}
