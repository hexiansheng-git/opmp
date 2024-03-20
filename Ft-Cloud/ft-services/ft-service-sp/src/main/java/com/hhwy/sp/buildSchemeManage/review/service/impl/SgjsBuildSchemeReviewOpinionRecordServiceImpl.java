package com.hhwy.sp.buildSchemeManage.review.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewOpinionRecord;
import com.hhwy.sp.buildSchemeManage.review.mapper.SgjsBuildSchemeReviewOpinionRecordMapper;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeReviewOpinionRecordService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:48
 * @remark
 */
@Service
public class SgjsBuildSchemeReviewOpinionRecordServiceImpl implements ISgjsBuildSchemeReviewOpinionRecordService {

    @Autowired
    private SgjsBuildSchemeReviewOpinionRecordMapper sgjsBuildSchemeReviewOpinionRecordMapper;


    public SgjsBuildSchemeReviewOpinionRecord getSgjsBuildSchemeReviewOpinionRecord(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord) {
        return sgjsBuildSchemeReviewOpinionRecordMapper.getSgjsBuildSchemeReviewOpinionRecord(sgjsBuildSchemeReviewOpinionRecord);
    }

    public List<SgjsBuildSchemeReviewOpinionRecord> getSgjsBuildSchemeReviewOpinionRecordList(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord) {
        return sgjsBuildSchemeReviewOpinionRecordMapper.getSgjsBuildSchemeReviewOpinionRecordList(sgjsBuildSchemeReviewOpinionRecord);
    }

    @Transactional
    public int insertSgjsBuildSchemeReviewOpinionRecord(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord) {
        sgjsBuildSchemeReviewOpinionRecord.setId(IdWorker.createId());
        sgjsBuildSchemeReviewOpinionRecord.setCreateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeReviewOpinionRecord.setCreateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeReviewOpinionRecordMapper.insertSgjsBuildSchemeReviewOpinionRecord(sgjsBuildSchemeReviewOpinionRecord);
    }

    @Transactional
    public int insertSgjsBuildSchemeReviewOpinionRecordList(List<SgjsBuildSchemeReviewOpinionRecord> sgjsBuildSchemeReviewOpinionRecordList) {
        for (SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord : sgjsBuildSchemeReviewOpinionRecordList) {
            sgjsBuildSchemeReviewOpinionRecord.setId(IdWorker.createId());
            sgjsBuildSchemeReviewOpinionRecord.setCreateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeReviewOpinionRecord.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeReviewOpinionRecordMapper.insertSgjsBuildSchemeReviewOpinionRecordList(sgjsBuildSchemeReviewOpinionRecordList);
    }

    @Transactional
    public int updateSgjsBuildSchemeReviewOpinionRecord(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord) {
        sgjsBuildSchemeReviewOpinionRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeReviewOpinionRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeReviewOpinionRecordMapper.updateSgjsBuildSchemeReviewOpinionRecord(sgjsBuildSchemeReviewOpinionRecord);
    }

    @Transactional
    public int updateSgjsBuildSchemeReviewOpinionRecordList(List<SgjsBuildSchemeReviewOpinionRecord> sgjsBuildSchemeReviewOpinionRecordList) {
        for (SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord : sgjsBuildSchemeReviewOpinionRecordList) {
            sgjsBuildSchemeReviewOpinionRecord.setUpdateUser(SecurityUtils.getUserName());
            sgjsBuildSchemeReviewOpinionRecord.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsBuildSchemeReviewOpinionRecordMapper.updateSgjsBuildSchemeReviewOpinionRecordList(sgjsBuildSchemeReviewOpinionRecordList);
    }

    @Transactional
    public int deleteSgjsBuildSchemeReviewOpinionRecord(SgjsBuildSchemeReviewOpinionRecord sgjsBuildSchemeReviewOpinionRecord) {
        sgjsBuildSchemeReviewOpinionRecord.setUpdateUser(SecurityUtils.getUserName());
        sgjsBuildSchemeReviewOpinionRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsBuildSchemeReviewOpinionRecordMapper.deleteSgjsBuildSchemeReviewOpinionRecord(sgjsBuildSchemeReviewOpinionRecord);
    }

    @Transactional
    public int deleteSgjsBuildSchemeReviewOpinionRecordByPks(List<Long> sgjsBuildSchemeReviewOpinionRecordPkList) {
        return sgjsBuildSchemeReviewOpinionRecordMapper.deleteSgjsBuildSchemeReviewOpinionRecordByPks(sgjsBuildSchemeReviewOpinionRecordPkList);
    }
}
