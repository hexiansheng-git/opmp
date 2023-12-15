package com.hhwy.sd.equipEntryRecord.service;

import java.util.List;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecordInfo;

/**
 * @author zmh
 * @date 2023-12-14 11:08:15
 * @remark
 */
public interface IKcsjEquipEntryRecordInfoService {

    KcsjEquipEntryRecordInfo getKcsjEquipEntryRecordInfo(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo);

    List<KcsjEquipEntryRecordInfo> getKcsjEquipEntryRecordInfoList(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo);

    int insertKcsjEquipEntryRecordInfo(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo);

    int insertKcsjEquipEntryRecordInfoList(List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList);

    int updateKcsjEquipEntryRecordInfo(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo);

    int updateKcsjEquipEntryRecordInfoList(List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList);

    int deleteKcsjEquipEntryRecordInfo(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo);

    int deleteKcsjEquipEntryRecordInfoByPks(List<Long> kcsjEquipEntryRecordInfoPkList);

    List<KcsjEquipEntryRecordInfo> getIds(List<Long> ids);
}
