package com.hhwy.sd.equipEntryRecord.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecordInfo;

/**
 * @author zmh
 * @date 2023-12-14 11:08:15
 * @remark
 */
public interface KcsjEquipEntryRecordInfoMapper {

    KcsjEquipEntryRecordInfo getKcsjEquipEntryRecordInfo(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo);

    List<KcsjEquipEntryRecordInfo> getKcsjEquipEntryRecordInfoList(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo);

    int insertKcsjEquipEntryRecordInfo(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo);

    int insertKcsjEquipEntryRecordInfoList(@Param("kcsjEquipEntryRecordInfoList") List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList);

    int updateKcsjEquipEntryRecordInfo(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo);

    int updateKcsjEquipEntryRecordInfoList(@Param("kcsjEquipEntryRecordInfoList") List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList);

    int deleteKcsjEquipEntryRecordInfo(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo);

    int deleteKcsjEquipEntryRecordInfoByPks(@Param("kcsjEquipEntryRecordInfoPkList") List<Long> kcsjEquipEntryRecordInfoPkList);
}
