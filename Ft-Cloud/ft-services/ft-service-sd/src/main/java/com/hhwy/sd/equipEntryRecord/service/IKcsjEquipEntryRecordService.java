package com.hhwy.sd.equipEntryRecord.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecord;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecordVo;
import java.util.List;

/**
 * @author zmh
 * @date 2023-12-14 11:08:08
 * @remark
 */
public interface IKcsjEquipEntryRecordService {

    KcsjEquipEntryRecord getKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord);

    KcsjEquipEntryRecordVo getKcsjEquipEntryRecordList(KcsjEquipEntryRecord kcsjEquipEntryRecord);

    int insertKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord);

    AjaxResult insertKcsjEquipEntryRecordList(KcsjEquipEntryRecordVo kcsjEquipEntryRecordList);

    int updateKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord);

    int updateKcsjEquipEntryRecordList(List<KcsjEquipEntryRecord> kcsjEquipEntryRecordList);

    int deleteKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord);

    int deleteKcsjEquipEntryRecordByPks(List<Long> kcsjEquipEntryRecordPkList);

    KcsjEquipEntryRecordVo sync();
}
