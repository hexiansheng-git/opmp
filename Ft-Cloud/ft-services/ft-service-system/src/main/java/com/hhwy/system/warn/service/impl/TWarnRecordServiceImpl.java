package com.hhwy.system.warn.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.system.warn.domain.TWarnRecord;
import com.hhwy.system.warn.mapper.TWarnRecordMapper;
import com.hhwy.system.warn.service.ITWarnRecordService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-09-26 17:52:28
 * @remark
 */
@Service
public class TWarnRecordServiceImpl implements ITWarnRecordService {

    @Autowired
    private TWarnRecordMapper tWarnRecordMapper;


    public TWarnRecord getTWarnRecord(TWarnRecord tWarnRecord) {
        return tWarnRecordMapper.getTWarnRecord(tWarnRecord);
    }

    public List<TWarnRecord> getTWarnRecordList(TWarnRecord tWarnRecord) {
        return tWarnRecordMapper.getTWarnRecordList(tWarnRecord);
    }

    @Transactional
    public int insertTWarnRecord(TWarnRecord tWarnRecord) {
        tWarnRecord.setId(IdWorker.createId());
        tWarnRecord.setCreateUser(SecurityUtils.getUserName());
        tWarnRecord.setCreateTime(DateUtils.getNowDate());
        return tWarnRecordMapper.insertTWarnRecord(tWarnRecord);
    }

    @Transactional
    public int insertTWarnRecordList(List<TWarnRecord> tWarnRecordList) {
        for (TWarnRecord tWarnRecord : tWarnRecordList) {
            tWarnRecord.setId(IdWorker.createId());
            tWarnRecord.setCreateUser(SecurityUtils.getUserName());
            tWarnRecord.setCreateTime(DateUtils.getNowDate());
        }
        return tWarnRecordMapper.insertTWarnRecordList(tWarnRecordList);
    }

    @Transactional
    public int updateTWarnRecord(TWarnRecord tWarnRecord) {
        tWarnRecord.setUpdateUser(SecurityUtils.getUserName());
        tWarnRecord.setUpdateTime(DateUtils.getNowDate());
        return tWarnRecordMapper.updateTWarnRecord(tWarnRecord);
    }

    @Transactional
    public int updateTWarnRecordList(List<TWarnRecord> tWarnRecordList) {
        for (TWarnRecord tWarnRecord : tWarnRecordList) {
            tWarnRecord.setUpdateUser(SecurityUtils.getUserName());
            tWarnRecord.setUpdateTime(DateUtils.getNowDate());
        }
        return tWarnRecordMapper.updateTWarnRecordList(tWarnRecordList);
    }

    @Transactional
    public int deleteTWarnRecord(TWarnRecord tWarnRecord) {
        tWarnRecord.setUpdateUser(SecurityUtils.getUserName());
        tWarnRecord.setUpdateTime(DateUtils.getNowDate());
        return tWarnRecordMapper.deleteTWarnRecord(tWarnRecord);
    }

    @Transactional
    public int deleteTWarnRecordByPks(List<Long> tWarnRecordPkList) {
        return tWarnRecordMapper.deleteTWarnRecordByPks(tWarnRecordPkList);
    }
}
