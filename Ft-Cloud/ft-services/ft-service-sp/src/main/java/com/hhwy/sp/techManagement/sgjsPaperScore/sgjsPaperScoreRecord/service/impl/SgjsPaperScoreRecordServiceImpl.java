package com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.domain.SgjsPaperScoreRecord;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.mapper.SgjsPaperScoreRecordMapper;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreRecord.service.ISgjsPaperScoreRecordService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fsd
 * @date 2024-07-10 16:38:37
 * @remark
 */
@Service
public class SgjsPaperScoreRecordServiceImpl implements ISgjsPaperScoreRecordService {

    @Autowired
    private SgjsPaperScoreRecordMapper sgjsPaperScoreRecordMapper;


    public SgjsPaperScoreRecord getSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord) {
        return sgjsPaperScoreRecordMapper.getSgjsPaperScoreRecord(sgjsPaperScoreRecord);
    }

    public List<SgjsPaperScoreRecord> getSgjsPaperScoreRecordList(SgjsPaperScoreRecord sgjsPaperScoreRecord) {
        return sgjsPaperScoreRecordMapper.getSgjsPaperScoreRecordList(sgjsPaperScoreRecord);
    }

    @Transactional
    public int insertSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord) {
        sgjsPaperScoreRecord.setId(IdWorker.createId());
        sgjsPaperScoreRecord.setCreateUser(SecurityUtils.getUserName());
        sgjsPaperScoreRecord.setCreateTime(DateUtils.getNowDate());
        return sgjsPaperScoreRecordMapper.insertSgjsPaperScoreRecord(sgjsPaperScoreRecord);
    }

    @Transactional
    public int insertSgjsPaperScoreRecordList(List<SgjsPaperScoreRecord> sgjsPaperScoreRecordList) {
        for (SgjsPaperScoreRecord sgjsPaperScoreRecord : sgjsPaperScoreRecordList) {
            sgjsPaperScoreRecord.setId(IdWorker.createId());
            sgjsPaperScoreRecord.setCreateUser(SecurityUtils.getUserName());
            sgjsPaperScoreRecord.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsPaperScoreRecordMapper.insertSgjsPaperScoreRecordList(sgjsPaperScoreRecordList);
    }

    @Transactional
    public int updateSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord) {
        sgjsPaperScoreRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperScoreRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperScoreRecordMapper.updateSgjsPaperScoreRecord(sgjsPaperScoreRecord);
    }

    @Transactional
    public int updateSgjsPaperScoreRecordList(List<SgjsPaperScoreRecord> sgjsPaperScoreRecordList) {
        for (SgjsPaperScoreRecord sgjsPaperScoreRecord : sgjsPaperScoreRecordList) {
            sgjsPaperScoreRecord.setUpdateUser(SecurityUtils.getUserName());
            sgjsPaperScoreRecord.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsPaperScoreRecordMapper.updateSgjsPaperScoreRecordList(sgjsPaperScoreRecordList);
    }

    @Transactional
    public int deleteSgjsPaperScoreRecord(SgjsPaperScoreRecord sgjsPaperScoreRecord) {
        sgjsPaperScoreRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsPaperScoreRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsPaperScoreRecordMapper.deleteSgjsPaperScoreRecord(sgjsPaperScoreRecord);
    }

    @Transactional
    public int deleteSgjsPaperScoreRecordByPks(List<Long> sgjsPaperScoreRecordPkList) {
        return sgjsPaperScoreRecordMapper.deleteSgjsPaperScoreRecordByPks(sgjsPaperScoreRecordPkList);
    }
}
