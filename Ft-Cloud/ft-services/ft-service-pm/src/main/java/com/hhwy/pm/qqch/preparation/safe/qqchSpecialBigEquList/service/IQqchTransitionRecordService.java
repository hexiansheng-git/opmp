package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service;

import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchTransitionRecord;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-08 11:37:26
 * @remark 
 */
public interface IQqchTransitionRecordService {
                                                                                                                                                                                                                                                                                                                
    QqchTransitionRecord getQqchTransitionRecord(QqchTransitionRecord qqchTransitionRecord);

    List<QqchTransitionRecord> getQqchTransitionRecordList(QqchTransitionRecord qqchTransitionRecord);

    int insertQqchTransitionRecord(QqchTransitionRecord qqchTransitionRecord);

    int insertQqchTransitionRecordList(List<QqchTransitionRecord> qqchTransitionRecordList);

    int updateQqchTransitionRecord(QqchTransitionRecord qqchTransitionRecord);

     int updateQqchTransitionRecordList(List<QqchTransitionRecord> qqchTransitionRecordList);
    
    int deleteQqchTransitionRecord(QqchTransitionRecord qqchTransitionRecord);

    int deleteQqchTransitionRecordByPks(List<Long> qqchTransitionRecordPkList);
    }
