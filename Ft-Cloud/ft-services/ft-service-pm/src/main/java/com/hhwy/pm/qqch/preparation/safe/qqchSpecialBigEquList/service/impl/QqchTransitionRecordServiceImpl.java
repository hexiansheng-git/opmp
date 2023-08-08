package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchTransitionRecord;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.mapper.QqchTransitionRecordMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service.IQqchTransitionRecordService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-08 11:37:26
 * @remark 
 */
@Service
public class QqchTransitionRecordServiceImpl implements IQqchTransitionRecordService {

    @Autowired
    private QqchTransitionRecordMapper qqchTransitionRecordMapper;

                                                                                                                                                                                                                                                                                                                
    public QqchTransitionRecord getQqchTransitionRecord(QqchTransitionRecord qqchTransitionRecord) {
        return qqchTransitionRecordMapper.getQqchTransitionRecord(qqchTransitionRecord);
    }

    public List<QqchTransitionRecord> getQqchTransitionRecordList(QqchTransitionRecord qqchTransitionRecord) {
        return qqchTransitionRecordMapper.getQqchTransitionRecordList(qqchTransitionRecord);
    }

    @Transactional
    public int insertQqchTransitionRecord(QqchTransitionRecord qqchTransitionRecord) {
        qqchTransitionRecord.setId(IdWorker.createId());
        qqchTransitionRecord.setCreateUser(SecurityUtils.getUserName());
        qqchTransitionRecord.setCreateTime(DateUtils.getNowDate());
        return qqchTransitionRecordMapper.insertQqchTransitionRecord(qqchTransitionRecord);
    }

    @Transactional
    public int insertQqchTransitionRecordList(List<QqchTransitionRecord> qqchTransitionRecordList) {
        for (QqchTransitionRecord qqchTransitionRecord : qqchTransitionRecordList) {
            qqchTransitionRecord.setId(IdWorker.createId());
            qqchTransitionRecord.setCreateUser(SecurityUtils.getUserName());
            qqchTransitionRecord.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTransitionRecordMapper.insertQqchTransitionRecordList(qqchTransitionRecordList);
    }

    @Transactional
    public int updateQqchTransitionRecord(QqchTransitionRecord qqchTransitionRecord) {
        qqchTransitionRecord.setUpdateUser(SecurityUtils.getUserName());
        qqchTransitionRecord.setUpdateTime(DateUtils.getNowDate());
        return qqchTransitionRecordMapper.updateQqchTransitionRecord(qqchTransitionRecord);
    }

            @Transactional
        public int updateQqchTransitionRecordList(List<QqchTransitionRecord> qqchTransitionRecordList) {
            for (QqchTransitionRecord qqchTransitionRecord : qqchTransitionRecordList) {
                qqchTransitionRecord.setUpdateUser(SecurityUtils.getUserName());
                qqchTransitionRecord.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchTransitionRecordMapper.updateQqchTransitionRecordList(qqchTransitionRecordList);
        }
    
    @Transactional
    public int deleteQqchTransitionRecord(QqchTransitionRecord qqchTransitionRecord) {
        qqchTransitionRecord.setUpdateUser(SecurityUtils.getUserName());
        qqchTransitionRecord.setUpdateTime(DateUtils.getNowDate());
        return qqchTransitionRecordMapper.deleteQqchTransitionRecord(qqchTransitionRecord);
    }

            @Transactional
        public int deleteQqchTransitionRecordByPks(List<Long> qqchTransitionRecordPkList) {
            return qqchTransitionRecordMapper.deleteQqchTransitionRecordByPks(qqchTransitionRecordPkList);
        }
    }
