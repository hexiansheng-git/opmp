package com.hhwy.sd.disclosureRecord.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.disclosureRecord.domain.KcsjDisclosureRecord;
import com.hhwy.sd.disclosureRecord.mapper.KcsjDisclosureRecordMapper;
import com.hhwy.sd.disclosureRecord.service.IKcsjDisclosureRecordService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-12-18 11:15:48
 * @remark
 */
@Service
public class KcsjDisclosureRecordServiceImpl implements IKcsjDisclosureRecordService {

    @Autowired
    private KcsjDisclosureRecordMapper kcsjDisclosureRecordMapper;


    public KcsjDisclosureRecord getKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord) {
        return kcsjDisclosureRecordMapper.getKcsjDisclosureRecord(kcsjDisclosureRecord);
    }

    public List<KcsjDisclosureRecord> getKcsjDisclosureRecordList(KcsjDisclosureRecord kcsjDisclosureRecord) {
        return kcsjDisclosureRecordMapper.getKcsjDisclosureRecordList(kcsjDisclosureRecord);
    }

    @Transactional
    public int insertKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord) {
        kcsjDisclosureRecord.setId(IdWorker.createId());
        kcsjDisclosureRecord.setCreateUser(SecurityUtils.getUserName());
        kcsjDisclosureRecord.setCreateTime(DateUtils.getNowDate());
        return kcsjDisclosureRecordMapper.insertKcsjDisclosureRecord(kcsjDisclosureRecord);
    }

    @Transactional
    public int insertKcsjDisclosureRecordList(List<KcsjDisclosureRecord> kcsjDisclosureRecordList) {
        for (KcsjDisclosureRecord kcsjDisclosureRecord : kcsjDisclosureRecordList) {
            kcsjDisclosureRecord.setId(IdWorker.createId());
            kcsjDisclosureRecord.setCreateUser(SecurityUtils.getUserName());
            kcsjDisclosureRecord.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjDisclosureRecordMapper.insertKcsjDisclosureRecordList(kcsjDisclosureRecordList);
    }

    @Transactional
    public int updateKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord) {
        kcsjDisclosureRecord.setUpdateUser(SecurityUtils.getUserName());
        kcsjDisclosureRecord.setUpdateTime(DateUtils.getNowDate());
        return kcsjDisclosureRecordMapper.updateKcsjDisclosureRecord(kcsjDisclosureRecord);
    }

    @Transactional
    public int updateKcsjDisclosureRecordList(List<KcsjDisclosureRecord> kcsjDisclosureRecordList) {
        for (KcsjDisclosureRecord kcsjDisclosureRecord : kcsjDisclosureRecordList) {
            kcsjDisclosureRecord.setUpdateUser(SecurityUtils.getUserName());
            kcsjDisclosureRecord.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjDisclosureRecordMapper.updateKcsjDisclosureRecordList(kcsjDisclosureRecordList);
    }

    @Transactional
    public int deleteKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord) {
        kcsjDisclosureRecord.setUpdateUser(SecurityUtils.getUserName());
        kcsjDisclosureRecord.setUpdateTime(DateUtils.getNowDate());
        return kcsjDisclosureRecordMapper.deleteKcsjDisclosureRecord(kcsjDisclosureRecord);
    }

    @Transactional
    public int deleteKcsjDisclosureRecordByPks(List<Long> kcsjDisclosureRecordPkList) {
        return kcsjDisclosureRecordMapper.deleteKcsjDisclosureRecordByPks(kcsjDisclosureRecordPkList);
    }
}
