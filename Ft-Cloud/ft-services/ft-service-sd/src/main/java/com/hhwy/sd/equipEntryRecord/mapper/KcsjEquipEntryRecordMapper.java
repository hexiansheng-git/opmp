package com.hhwy.sd.equipEntryRecord.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecord;

/**
 * @author zmh
 * @date 2023-12-14 11:08:08
 * @remark
 */
public interface KcsjEquipEntryRecordMapper {

    KcsjEquipEntryRecord getKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord);

    List<KcsjEquipEntryRecord> getKcsjEquipEntryRecordList(KcsjEquipEntryRecord kcsjEquipEntryRecord);

    int insertKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord);

    int insertKcsjEquipEntryRecordList(@Param("kcsjEquipEntryRecordList") List<KcsjEquipEntryRecord> kcsjEquipEntryRecordList);

    int updateKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord);

    int updateKcsjEquipEntryRecordList(@Param("list") List<KcsjEquipEntryRecord> kcsjEquipEntryRecordList);

    int deleteKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord);

    int deleteKcsjEquipEntryRecordByPks(@Param("kcsjEquipEntryRecordPkList") List<Long> kcsjEquipEntryRecordPkList);

    int deleteInfoData(List<KcsjEquipEntryRecord> list);

    void delEquipEntryAll(KcsjEquipEntryRecord kcsjEquipEntryRecord);
}
