package com.hhwy.sp.buildSchemeManage.review.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeStaffOpinionRecord;
import com.hhwy.sp.buildSchemeManage.review.mapper.SgjsBuildSchemeStaffOpinionRecordMapper;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeStaffOpinionRecordService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:40:06
 * @remark
 */
@Service
public class SgjsBuildSchemeStaffOpinionRecordServiceImpl implements ISgjsBuildSchemeStaffOpinionRecordService {

    @Autowired
    private SgjsBuildSchemeStaffOpinionRecordMapper sgjsBuildSchemeStaffOpinionRecordMapper;


    public SgjsBuildSchemeStaffOpinionRecord getSgjsBuildSchemeStaffOpinionRecord(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord) {
        return sgjsBuildSchemeStaffOpinionRecordMapper.getSgjsBuildSchemeStaffOpinionRecord(sgjsBuildSchemeStaffOpinionRecord);
    }

    public List<SgjsBuildSchemeStaffOpinionRecord> getSgjsBuildSchemeStaffOpinionRecordList(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord) {
        return sgjsBuildSchemeStaffOpinionRecordMapper.getSgjsBuildSchemeStaffOpinionRecordList(sgjsBuildSchemeStaffOpinionRecord);
    }

    @Transactional
    public int insertSgjsBuildSchemeStaffOpinionRecord(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord) {
        sgjsBuildSchemeStaffOpinionRecord.setId(IdWorker.createId());
        sgjsBuildSchemeStaffOpinionRecord.setCreateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeStaffOpinionRecord.setCreateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeStaffOpinionRecordMapper.insertSgjsBuildSchemeStaffOpinionRecord(sgjsBuildSchemeStaffOpinionRecord);
    }

    @Transactional
    public int insertSgjsBuildSchemeStaffOpinionRecordList(List<SgjsBuildSchemeStaffOpinionRecord> sgjsBuildSchemeStaffOpinionRecordList) {
        for (SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord : sgjsBuildSchemeStaffOpinionRecordList) {
            sgjsBuildSchemeStaffOpinionRecord.setId(IdWorker.createId());
            sgjsBuildSchemeStaffOpinionRecord.setCreateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeStaffOpinionRecord.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeStaffOpinionRecordMapper.insertSgjsBuildSchemeStaffOpinionRecordList(sgjsBuildSchemeStaffOpinionRecordList);
    }

    @Transactional
    public int updateSgjsBuildSchemeStaffOpinionRecord(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord) {
        sgjsBuildSchemeStaffOpinionRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeStaffOpinionRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeStaffOpinionRecordMapper.updateSgjsBuildSchemeStaffOpinionRecord(sgjsBuildSchemeStaffOpinionRecord);
    }

    @Transactional
    public int updateSgjsBuildSchemeStaffOpinionRecordList(List<SgjsBuildSchemeStaffOpinionRecord> sgjsBuildSchemeStaffOpinionRecordList) {
        for (SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord : sgjsBuildSchemeStaffOpinionRecordList) {
            sgjsBuildSchemeStaffOpinionRecord.setUpdateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeStaffOpinionRecord.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeStaffOpinionRecordMapper.updateSgjsBuildSchemeStaffOpinionRecordList(sgjsBuildSchemeStaffOpinionRecordList);
    }

    @Transactional
    public int deleteSgjsBuildSchemeStaffOpinionRecord(SgjsBuildSchemeStaffOpinionRecord sgjsBuildSchemeStaffOpinionRecord) {
        sgjsBuildSchemeStaffOpinionRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeStaffOpinionRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeStaffOpinionRecordMapper.deleteSgjsBuildSchemeStaffOpinionRecord(sgjsBuildSchemeStaffOpinionRecord);
    }

    @Transactional
    public int deleteSgjsBuildSchemeStaffOpinionRecordByPks(List<Long> sgjsBuildSchemeStaffOpinionRecordPkList) {
        return sgjsBuildSchemeStaffOpinionRecordMapper.deleteSgjsBuildSchemeStaffOpinionRecordByPks(sgjsBuildSchemeStaffOpinionRecordPkList);
    }
}
