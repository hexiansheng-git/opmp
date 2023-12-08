package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.domain.SgjsEquipEntryRecord;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.mapper.SgjsEquipEntryRecordMapper;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.service.ISgjsEquipEntryRecordService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:47:00
 * @remark 
 */
@Service
public class SgjsEquipEntryRecordServiceImpl implements ISgjsEquipEntryRecordService{

    @Autowired
    private SgjsEquipEntryRecordMapper sgjsEquipEntryRecordMapper;

                                                                                                                                                                                                                                                                                                                                                                                        
    public SgjsEquipEntryRecord getSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        return sgjsEquipEntryRecordMapper.getSgjsEquipEntryRecord(sgjsEquipEntryRecord);
    }

    public List<SgjsEquipEntryRecord> getSgjsEquipEntryRecordList(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        return sgjsEquipEntryRecordMapper.getSgjsEquipEntryRecordList(sgjsEquipEntryRecord);
    }

    @Transactional
    public int insertSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        sgjsEquipEntryRecord.setId(IdWorker.createId());
        sgjsEquipEntryRecord.setCreateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecord.setCreateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordMapper.insertSgjsEquipEntryRecord(sgjsEquipEntryRecord);
    }

    @Transactional
    public int insertSgjsEquipEntryRecordList(List<SgjsEquipEntryRecord> sgjsEquipEntryRecordList) {
        for (SgjsEquipEntryRecord sgjsEquipEntryRecord : sgjsEquipEntryRecordList) {
            sgjsEquipEntryRecord.setId(IdWorker.createId());
            sgjsEquipEntryRecord.setCreateUser(SecurityUtils.getUserName());
            sgjsEquipEntryRecord.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsEquipEntryRecordMapper.insertSgjsEquipEntryRecordList(sgjsEquipEntryRecordList);
    }

    @Transactional
    public int updateSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        sgjsEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordMapper.updateSgjsEquipEntryRecord(sgjsEquipEntryRecord);
    }

            @Transactional
        public int updateSgjsEquipEntryRecordList(List<SgjsEquipEntryRecord> sgjsEquipEntryRecordList) {
            for (SgjsEquipEntryRecord sgjsEquipEntryRecord : sgjsEquipEntryRecordList) {
                sgjsEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
                sgjsEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
            }
            return sgjsEquipEntryRecordMapper.updateSgjsEquipEntryRecordList(sgjsEquipEntryRecordList);
        }
    
    @Transactional
    public int deleteSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord) {
        sgjsEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsEquipEntryRecordMapper.deleteSgjsEquipEntryRecord(sgjsEquipEntryRecord);
    }

            @Transactional
        public int deleteSgjsEquipEntryRecordByPks(List<Long> sgjsEquipEntryRecordPkList) {
            return sgjsEquipEntryRecordMapper.deleteSgjsEquipEntryRecordByPks(sgjsEquipEntryRecordPkList);
        }
    }
