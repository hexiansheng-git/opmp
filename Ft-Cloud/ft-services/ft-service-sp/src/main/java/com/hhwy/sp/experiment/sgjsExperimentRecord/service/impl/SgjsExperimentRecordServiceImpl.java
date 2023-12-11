package com.hhwy.sp.experiment.sgjsExperimentRecord.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.experiment.sgjsExperimentRecord.domain.SgjsExperimentRecord;
import com.hhwy.sp.experiment.sgjsExperimentRecord.mapper.SgjsExperimentRecordMapper;
import com.hhwy.sp.experiment.sgjsExperimentRecord.service.ISgjsExperimentRecordService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lcf--试验设备进场记录
 * @date 2023-12-11 15:03:30
 * @remark 
 */
@Service
public class SgjsExperimentRecordServiceImpl implements ISgjsExperimentRecordService{

    @Autowired
    private SgjsExperimentRecordMapper sgjsExperimentRecordMapper;

                                                                                                                                                                                                                                                                                                                                                                                        
    public SgjsExperimentRecord getSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord) {
        return sgjsExperimentRecordMapper.getSgjsExperimentRecord(sgjsExperimentRecord);
    }

    public List<SgjsExperimentRecord> getSgjsExperimentRecordList(SgjsExperimentRecord sgjsExperimentRecord) {
        return sgjsExperimentRecordMapper.getSgjsExperimentRecordList(sgjsExperimentRecord);
    }

    @Transactional
    public int insertSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord) {
        sgjsExperimentRecord.setId(IdWorker.createId());
        sgjsExperimentRecord.setCreateUser(SecurityUtils.getUserName());
        sgjsExperimentRecord.setCreateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordMapper.insertSgjsExperimentRecord(sgjsExperimentRecord);
    }

    @Transactional
    public int insertSgjsExperimentRecordList(List<SgjsExperimentRecord> sgjsExperimentRecordList) {
        for (SgjsExperimentRecord sgjsExperimentRecord : sgjsExperimentRecordList) {
            sgjsExperimentRecord.setId(IdWorker.createId());
            sgjsExperimentRecord.setCreateUser(SecurityUtils.getUserName());
            sgjsExperimentRecord.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsExperimentRecordMapper.insertSgjsExperimentRecordList(sgjsExperimentRecordList);
    }

    @Transactional
    public int updateSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord) {
        sgjsExperimentRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordMapper.updateSgjsExperimentRecord(sgjsExperimentRecord);
    }

            @Transactional
        public int updateSgjsExperimentRecordList(List<SgjsExperimentRecord> sgjsExperimentRecordList) {
            for (SgjsExperimentRecord sgjsExperimentRecord : sgjsExperimentRecordList) {
                sgjsExperimentRecord.setUpdateUser(SecurityUtils.getUserName());
                sgjsExperimentRecord.setUpdateTime(DateUtils.getNowDate());
            }
            return sgjsExperimentRecordMapper.updateSgjsExperimentRecordList(sgjsExperimentRecordList);
        }
    
    @Transactional
    public int deleteSgjsExperimentRecord(SgjsExperimentRecord sgjsExperimentRecord) {
        sgjsExperimentRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsExperimentRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsExperimentRecordMapper.deleteSgjsExperimentRecord(sgjsExperimentRecord);
    }

            @Transactional
        public int deleteSgjsExperimentRecordByPks(List<Long> sgjsExperimentRecordPkList) {
            return sgjsExperimentRecordMapper.deleteSgjsExperimentRecordByPks(sgjsExperimentRecordPkList);
        }
    }
