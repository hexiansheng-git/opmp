package com.hhwy.sd.equipEntryRecord.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sd.equipEntryRecord.mapper.KcsjEquipEntryRecordMapper;
import com.hhwy.sd.equipEntryRecord.service.IKcsjEquipEntryRecordService;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecord;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author zmh
 * @date 2023-12-14 11:08:08
 * @remark
 */
@Service
public class KcsjEquipEntryRecordServiceImpl implements IKcsjEquipEntryRecordService {

    @Autowired
    private KcsjEquipEntryRecordMapper kcsjEquipEntryRecordMapper;


    public KcsjEquipEntryRecord getKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord) {
        return kcsjEquipEntryRecordMapper.getKcsjEquipEntryRecord(kcsjEquipEntryRecord);
    }

    public List<KcsjEquipEntryRecord> getKcsjEquipEntryRecordList(
        KcsjEquipEntryRecord kcsjEquipEntryRecord) {
        return kcsjEquipEntryRecordMapper.getKcsjEquipEntryRecordList(kcsjEquipEntryRecord);
    }

    @Transactional
    public int insertKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord) {
        kcsjEquipEntryRecord.setId(IdWorker.createId());
        kcsjEquipEntryRecord.setCreateUser(SecurityUtils.getUserName());
        kcsjEquipEntryRecord.setCreateTime(DateUtils.getNowDate());
        return kcsjEquipEntryRecordMapper.insertKcsjEquipEntryRecord(kcsjEquipEntryRecord);
    }

    @Transactional
    public int insertKcsjEquipEntryRecordList(List<KcsjEquipEntryRecord> kcsjEquipEntryRecordList) {
        for (KcsjEquipEntryRecord kcsjEquipEntryRecord : kcsjEquipEntryRecordList) {
            kcsjEquipEntryRecord.setId(IdWorker.createId());
            kcsjEquipEntryRecord.setCreateUser(SecurityUtils.getUserName());
            kcsjEquipEntryRecord.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjEquipEntryRecordMapper.insertKcsjEquipEntryRecordList(kcsjEquipEntryRecordList);
    }

    @Transactional
    public int updateKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord) {
        kcsjEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
        kcsjEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
        return kcsjEquipEntryRecordMapper.updateKcsjEquipEntryRecord(kcsjEquipEntryRecord);
    }

    @Transactional
    public int updateKcsjEquipEntryRecordList(List<KcsjEquipEntryRecord> kcsjEquipEntryRecordList) {
        for (KcsjEquipEntryRecord kcsjEquipEntryRecord : kcsjEquipEntryRecordList) {
            kcsjEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
            kcsjEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjEquipEntryRecordMapper.updateKcsjEquipEntryRecordList(kcsjEquipEntryRecordList);
    }

    @Transactional
    public int deleteKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord) {
        kcsjEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
        kcsjEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
        return kcsjEquipEntryRecordMapper.deleteKcsjEquipEntryRecord(kcsjEquipEntryRecord);
    }

    @Transactional
    public int deleteKcsjEquipEntryRecordByPks(List<Long> kcsjEquipEntryRecordPkList) {
        return kcsjEquipEntryRecordMapper.deleteKcsjEquipEntryRecordByPks(kcsjEquipEntryRecordPkList);
    }
}
