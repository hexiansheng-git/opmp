package com.hhwy.sd.equipEntryRecord.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecord;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecordVo;
import com.hhwy.sd.equipEntryRecord.domain.SyncWusheEquipVo;

import java.util.List;
import java.util.Map;

/**
 * @author zmh
 * @date 2023-12-14 11:08:08
 * @remark
 */
public interface IKcsjEquipEntryRecordService {

    KcsjEquipEntryRecordVo getKcsjEquipEntryRecordList(KcsjEquipEntryRecord kcsjEquipEntryRecord);

    int insertKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord);

    AjaxResult insertKcsjEquipEntryRecordList(KcsjEquipEntryRecordVo kcsjEquipEntryRecordList);

    int updateKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord);

    int updateKcsjEquipEntryRecordList(List<KcsjEquipEntryRecord> kcsjEquipEntryRecordList);

    int deleteKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord);

    int deleteKcsjEquipEntryRecordByPks(List<Long> kcsjEquipEntryRecordPkList);

    KcsjEquipEntryRecordVo sync();

    /**
     * 物设同步
     *
     * @return
     */
    AjaxResult syncWushe(List<SyncWusheEquipVo> list);

    /**
     * 定时任务
     * 同步物设设备进场记录
     *
     * @return
     */
    void syncWusheJob();

    void doSendGm();

}
